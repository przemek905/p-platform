package com.plml.pplatform.security;

import com.plml.pplatform.users.UserPlatformService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.plml.pplatform.security.SecurityConstants.*;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserPlatformService userPlatformService;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserPlatformService userPlatformService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userPlatformService = userPlatformService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, REGISTRATION_CONFIRM_URL).permitAll()
                .antMatchers(HttpMethod.GET, USER_RESET_PASSWORD_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userPlatformService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
                //TODO
//                .exceptionHandling().accessDeniedPage("/my-error-page");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration applyPermitDefaultValues = new CorsConfiguration().applyPermitDefaultValues();
    applyPermitDefaultValues.addExposedHeader("Authorization");
    applyPermitDefaultValues.addAllowedMethod(HttpMethod.OPTIONS);
    applyPermitDefaultValues.addAllowedMethod(HttpMethod.PUT);
    applyPermitDefaultValues.addAllowedMethod(HttpMethod.DELETE);
    applyPermitDefaultValues.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", applyPermitDefaultValues);
    return source;
  }
}
