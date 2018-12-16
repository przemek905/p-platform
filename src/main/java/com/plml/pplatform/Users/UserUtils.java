package com.plml.pplatform.Users;

import com.plml.pplatform.Exceptions.UserEmailAlreadyExistException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class UserUtils {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserUtils(UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApplicationUser createNewUser(@RequestBody ApplicationUser user) {
        checkEmailExist(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    public boolean userAlreadyExists(ApplicationUser alreadyExistingUser) {
        ApplicationUser signingUser = userService.getUserByUsername(alreadyExistingUser.getUsername());
        return signingUser != null;
    }

    private void checkEmailExist(ApplicationUser user) {
        ApplicationUser signingUser = userService.getUserByEmail(user.getEmail());

        if (signingUser != null) {
            throw new UserEmailAlreadyExistException("This email address is already register in platform", 114);
        }
    }

}
