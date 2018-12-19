package com.plml.pplatform.Validations.Validators;

import com.plml.pplatform.Users.UserService;
import com.plml.pplatform.Validations.UserAlreadyExistConstrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistValidator implements ConstraintValidator<UserAlreadyExistConstrain, String> {

    public UserExistValidator() {
    }

    private UserService userService;

    public UserExistValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UserAlreadyExistConstrain constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUserByUsername(username) != null;
    }


}
