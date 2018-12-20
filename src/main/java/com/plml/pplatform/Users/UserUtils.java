package com.plml.pplatform.Users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class UserUtils {

    private UserPlatformService userPlatformService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserUtils(UserPlatformService userPlatformService,
                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userPlatformService = userPlatformService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApplicationUser createNewUser(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userPlatformService.save(user);
    }

}
