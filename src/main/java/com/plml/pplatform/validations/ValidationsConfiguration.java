package com.plml.pplatform.validations;

import com.plml.pplatform.users.UserPlatformService;
import com.plml.pplatform.validations.validators.UserExistValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationsConfiguration {

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Primary
    @Bean
    UserExistValidator userExistValidator(UserPlatformService userPlatformService) {
        return new UserExistValidator(userPlatformService);
    }

}