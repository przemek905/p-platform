package com.plml.pplatform.validations;

import com.plml.pplatform.validations.validators.UserExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAlreadyExistConstrain {
    String message() default "User with that nickname already exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
