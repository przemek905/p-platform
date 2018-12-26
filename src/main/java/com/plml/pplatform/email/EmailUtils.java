package com.plml.pplatform.email;

import org.springframework.mail.SimpleMailMessage;

public class EmailUtils {
    public static SimpleMailMessage createEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
