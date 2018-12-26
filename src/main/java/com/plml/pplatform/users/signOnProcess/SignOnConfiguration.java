package com.plml.pplatform.users.signOnProcess;

import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class SignOnConfiguration {

    @Bean
    RegistrationListener registrationListener(JavaMailSender mailSender, VerificationTokenRepository verificationTokenRepository) {
        return new RegistrationListener(mailSender, verificationTokenRepository);
    }

}
