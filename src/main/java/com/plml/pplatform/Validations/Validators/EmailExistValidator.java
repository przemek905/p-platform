package com.plml.pplatform.Validations.Validators;

import com.plml.pplatform.Users.UserPlatformService;
import com.plml.pplatform.Validations.EmailAlreadyExistConstrain;

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
