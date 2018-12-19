package com.plml.pplatform.Validations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfiguration {

//    @Bean
//    public javax.validation.Validator localValidatorFactoryBean() {
//        return new LocalValidatorFactoryBean();
//    }


//    @Primary
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
//    @Primary
//    @Bean
//    UserExistValidator userExistValidator(UserService userService) {
//        return new UserExistValidator(userService);
//    }

//    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
//        return autowireCapableBeanFactory;
//    }
//
//    public void setAutowireCapableBeanFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
//        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
//    }

//    @Primary
//    @Bean
//    public Validator validator() {
//        return new LocalValidatorFactoryBean();
//    }
//
//    @Primary
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
//        methodValidationPostProcessor.setValidator(validator());
//        return methodValidationPostProcessor;
//    }

//    @Primary
//    @Bean
//    public Validator validator(){
//        return new LocalValidatorFactoryBean();
//    }

//    @Autowired
//    private Validator userValidator;
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(userValidator);
//    }

}