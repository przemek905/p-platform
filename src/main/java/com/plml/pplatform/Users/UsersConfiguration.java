package com.plml.pplatform.Users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UsersConfiguration {
    @Bean
    public UserUtils userUtils(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new UserUtils(userService, bCryptPasswordEncoder);
    }
}
