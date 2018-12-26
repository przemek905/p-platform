package com.plml.pplatform.users.signOnProcess;

import com.plml.pplatform.email.EmailUtils;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.UUID;

import static com.plml.pplatform.email.EmailConstants.CONFIRM_MESSAGE_TEMPLATE;

public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);

    @Value("${registration.hostAddress}")
    private String hostAddress;

    private JavaMailSender mailSender;
    private VerificationTokenRepository verificationTokenRepository;

    public RegistrationListener(JavaMailSender mailSender, VerificationTokenRepository verificationTokenRepository) {
        this.mailSender = mailSender;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.sendRegistrationEmail(event);
    }

    private void sendRegistrationEmail(OnRegistrationCompleteEvent event) {
        ApplicationUser user = event.getApplicationUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = createVerificationToken(user, token);
        LOGGER.info("Creating verification token: {}, for user: {}", token, user);
        verificationTokenRepository.save(verificationToken);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = hostAddress + "/pplatform/registrationConfirm?token=" + token.trim();
        String message = CONFIRM_MESSAGE_TEMPLATE + "\n\n" + confirmationUrl + "\n";

        SimpleMailMessage email = EmailUtils.createEmail(recipientAddress, subject, message);

        LOGGER.info("Sending registration email to: {}", recipientAddress);
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
