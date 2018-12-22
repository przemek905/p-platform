package com.plml.pplatform.validations.validators;

import com.plml.pplatform.users.UserPlatformService;
import com.plml.pplatform.validations.UserAlreadyExistConstrain;

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
