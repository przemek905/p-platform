package com.plml.pplatform.Users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UsersConfiguration {
    @Bean
    public UserUtils userUtils(UserPlatformService userPlatformService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new UserUtils(userPlatformService, bCryptPasswordEncoder);
    }

    @Bean
    UserPlatformService userService(UserRepository userRepository) {
        return new UserPlatformService(userRepository);
    }
}
