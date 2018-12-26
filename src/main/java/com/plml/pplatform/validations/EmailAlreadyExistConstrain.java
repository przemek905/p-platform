package com.plml.pplatform.validations;

import com.plml.pplatform.validations.validators.EmailExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAlreadyExistConstrain {
    String message() default "This email address already exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
