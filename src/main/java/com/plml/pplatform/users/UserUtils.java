package com.plml.pplatform.users;

import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class UserUtils {

    private UserPlatformService userPlatformService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private VerificationTokenRepository verificationTokenRepository;


    public UserUtils(UserPlatformService userPlatformService,
                     BCryptPasswordEncoder bCryptPasswordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.userPlatformService = userPlatformService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public ApplicationUser createNewUser(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userPlatformService.saveUser(user);
    }

    public VerificationToken getVerificationTokenByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public ApplicationUser updatePlatformUser(ApplicationUser user) {
        return userPlatformService.saveUser(user);
    }

}
