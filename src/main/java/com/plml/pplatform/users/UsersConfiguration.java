package com.plml.pplatform.users;

import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UsersConfiguration {
    @Bean
    public UserUtils userUtils(UserPlatformService userPlatformService,
                               BCryptPasswordEncoder bCryptPasswordEncoder,
                               VerificationTokenRepository verificationTokenRepository) {
        return new UserUtils(userPlatformService, bCryptPasswordEncoder, verificationTokenRepository);
    }

    @Bean
    UserPlatformService userService(UserRepository userRepository) {
        return new UserPlatformService(userRepository);
    }
}
