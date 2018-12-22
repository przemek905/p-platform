package com.plml.pplatform.users.signOnProcess;

import com.plml.pplatform.email.EmailUtils;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.UUID;

public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private static final String CONFIRM_MESSAGE_TEMPLATE = "Welcome to the PPlatform! \n To activate your account please go to this link:\n";
    private static final String APP_PREFIX = "/pplatform/";
    @Value("${registration.hostAddress}")
    private String hostAddress;

    private JavaMailSender mailSender;
    private EmailUtils emailUtils;
    private VerificationTokenRepository verificationTokenRepository;

    public RegistrationListener(JavaMailSender mailSender, EmailUtils emailUtils, VerificationTokenRepository verificationTokenRepository) {
        this.mailSender = mailSender;
        this.emailUtils = emailUtils;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        ApplicationUser user = event.getApplicationUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = createVerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = hostAddress + "/regitrationConfirm.html?token=" + token;
        String message = CONFIRM_MESSAGE_TEMPLATE + "\n" + hostAddress + APP_PREFIX + confirmationUrl;

        SimpleMailMessage email = EmailUtils.createEmail(recipientAddress, subject, message);

        mailSender.send(email);
    }

    private VerificationToken createVerificationToken(ApplicationUser user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        Date expiryDate = DateUtils.addDays(new Date(), 1);
        verificationToken.setExpiryDate(expiryDate);
        return verificationToken;
    }
}
