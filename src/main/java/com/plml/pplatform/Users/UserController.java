package com.plml.pplatform.Users;

import com.plml.pplatform.Exceptions.UserAlreadyRegistredException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserUtils userUtils;

    public UserController(UserUtils userUtils) {
    	this.userUtils = userUtils;
    }

    @PostMapping("/sign-up")
    public ApplicationUser signUp(@RequestBody ApplicationUser user) {
    	if (userUtils.userAlreadyExists(user)) {
    		throw new UserAlreadyRegistredException("User already register in platform", 113);
    	}
		return userUtils.createNewUser(user);
	}


}
