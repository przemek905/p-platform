package com.plml.pplatform.users;

import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class UserUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);


    private UserPlatformService userPlatformService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private VerificationTokenRepository verificationTokenRepository;


    public UserUtils(UserPlatformService userPlatformService,
                     BCryptPasswordEncoder bCryptPasswordEncoder,
                     VerificationTokenRepository verificationTokenRepository) {
        this.userPlatformService = userPlatformService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public ApplicationUser createNewUser(@RequestBody ApplicationUser user) {
        LOGGER.info("Creating new user with username: {} and email: {}", user.getUsername(), user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userPlatformService.saveUser(user);
    }

    public ApplicationUser updateUserPassword(@RequestBody ApplicationUser user, String newPassword, boolean isReset) {
        LOGGER.info("Updating user password with username: {} and email: {}", user.getUsername(), user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setPasswordReseted(isReset);
        return userPlatformService.saveUser(user);
    }

    public VerificationToken getVerificationTokenByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public ApplicationUser updatePlatformUser(ApplicationUser user) {
        return userPlatformService.saveUser(user);
    }

    public boolean isValidOldPassword(ApplicationUser user, String oldPassword) {
        if (user.getPassword() != null || !user.getPassword().isEmpty()) {
            return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
        }
        return false;
    }
}
