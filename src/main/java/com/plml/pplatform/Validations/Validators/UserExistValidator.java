package com.plml.pplatform.Validations.Validators;

import com.plml.pplatform.Users.UserPlatformService;
import com.plml.pplatform.Validations.UserAlreadyExistConstrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistValidator implements ConstraintValidator<UserAlreadyExistConstrain, String> {

    private UserPlatformService userPlatformService;

    public UserExistValidator(UserPlatformService userPlatformService) {
        this.userPlatformService = userPlatformService;
    }

    @Override
    public void initialize(UserAlreadyExistConstrain constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userPlatformService.getUserByUsername(username) == null;
    }


}
