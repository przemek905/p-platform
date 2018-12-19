package com.plml.pplatform.Validations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfiguration {

//    @Bean
//    public javax.validation.Validator localValidatorFactoryBean() {
//        return new LocalValidatorFactoryBean();
//    }


//    @Bean
//    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {
//
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
//                .buildValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//
//        return validator;
//    }
//
//    @Bean
//    UserExistValidator userExistValidator(UserService userService) {
//        return new UserExistValidator(userService);
//    }
//
//    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
//        return autowireCapableBeanFactory;
//    }
//
//    public void setAutowireCapableBeanFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
//        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
//    }

}