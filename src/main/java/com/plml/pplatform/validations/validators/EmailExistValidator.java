package com.plml.pplatform.validations.validators;

import com.plml.pplatform.users.UserPlatformService;
import com.plml.pplatform.validations.EmailAlreadyExistConstrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailAlreadyExistConstrain, String> {

    private UserPlatformService userPlatformService;

    public EmailExistValidator(UserPlatformService userPlatformService) {
        this.userPlatformService = userPlatformService;
    }

    @Override
    public void initialize(EmailAlreadyExistConstrain constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userPlatformService.getUserByEmail(email) == null;
    }
}
