package com.plml.pplatform.users;

import com.plml.pplatform.email.EmailUtils;
import com.plml.pplatform.exceptions.*;
import com.plml.pplatform.http.PPlatformResponse;
import com.plml.pplatform.users.signOnProcess.OnRegistrationCompleteEvent;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

import static com.plml.pplatform.email.EmailConstants.RESET_MESSAGE_TEMPLATE;
import static com.plml.pplatform.security.SecurityConstants.CHARACTERS;

@RestController
@RequestMapping("/pplatform")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserUtils userUtils;
    private UserPlatformService userPlatformService;
    private JavaMailSender mailSender;

    @Value("${registration.hostAddress}")
    private String hostAddress;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public UserController(UserUtils userUtils, UserPlatformService userPlatformService, JavaMailSender mailSender) {
        this.userUtils = userUtils;
        this.userPlatformService = userPlatformService;
        this.mailSender = mailSender;
    }

    @Transactional
    @PostMapping("/signup")
    public ApplicationUser signup(@RequestBody @Valid ApplicationUser user) {
        ApplicationUser newUser = userUtils.createNewUser(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
        return newUser;
    }

    @GetMapping("/registrationConfirm")
    public ApplicationUser confirmRegistration(@RequestParam("token") String token) {
        LOGGER.info("Activate user account by verification token");
        VerificationToken verificationToken = userUtils.getVerificationTokenByToken(token);
        if (verificationToken == null) {
            throw new TokenNotExistException("Verification link is invalid. Please check correct copy of verification" +
                    " token at the end of the URL link.", PPlatformErrorCodes.TOKEN_NOT_EXIST.getErrorCode());
        }

        ApplicationUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new TokenExpiredException("Activation link is expired after 24 hours. To register please contact " +
                    "support.", PPlatformErrorCodes.TOKEN_EXPIRED.getErrorCode());
        }

        user.setEnabled(true);
        return userUtils.updatePlatformUser(user);
    }

    @GetMapping("/user/resetPassword")
    public ApplicationUser resetPassword(@RequestParam("email") String userEmail) {
        ApplicationUser user = userPlatformService.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User not found", PPlatformErrorCodes.USER_NOT_FOUND.getErrorCode());
        }
        String randomPassword = RandomStringUtils.random(7, CHARACTERS);
        userUtils.updateUserPassword(user, randomPassword, true);
        String message = RESET_MESSAGE_TEMPLATE + "\n\n" + randomPassword;
        SimpleMailMessage email = EmailUtils.createEmail(user.getEmail(), "Password PPlatform reset", message);
        mailSender.send(email);
        return user;
    }

    @GetMapping("/user/updatePassword")
    public PPlatformResponse changeUserPassword(@RequestParam("password") String password,
                                                @RequestParam("oldPassword") String oldPassword) {
        ApplicationUser user = userPlatformService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (!userUtils.isValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException("Old password is incorrect",
                    PPlatformErrorCodes.OLD_PASSWORD_INVALID.getErrorCode());
        }
        userUtils.updateUserPassword(user, password, false);
        return new PPlatformResponse("Password updated");
    }

}
