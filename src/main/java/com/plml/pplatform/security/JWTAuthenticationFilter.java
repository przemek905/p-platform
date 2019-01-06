package com.plml.pplatform.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.UserPlatformService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static com.plml.pplatform.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserPlatformService userPlatformService;

    public JWTAuthenticationFilter(String loginpath, AuthenticationManager authenticationManager, UserPlatformService userPlatformService) {
        this.setFilterProcessesUrl(loginpath);
        this.authenticationManager = authenticationManager;
        this.userPlatformService = userPlatformService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
        claims.put("roles", ((User) auth.getPrincipal()).getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        String token = Jwts.builder()
//                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(AUTHORIZATION_HEADER_STRING, TOKEN_PREFIX + token);

        ApplicationUser user = userPlatformService.getUserByUsername(((User) auth.getPrincipal()).getUsername());
        if (user.isPasswordReset()) {
            res.addHeader(RESET_PASSWORD_HEADER_STRING, "true");
        }
    }
}
