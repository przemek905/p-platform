package com.plml.pplatform.users;

import com.plml.pplatform.exceptions.PPlatformErrorCodes;
import com.plml.pplatform.exceptions.TokenExpiredException;
import com.plml.pplatform.exceptions.TokenNotExistException;
import com.plml.pplatform.users.signOnProcess.OnRegistrationCompleteEvent;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserUtils userUtils;

	@Autowired
    ApplicationEventPublisher eventPublisher;

    public UserController(UserUtils userUtils) {
    	this.userUtils = userUtils;
    }

    @PostMapping("/signup")
    public ApplicationUser signup(@RequestBody @Valid ApplicationUser user) {
        ApplicationUser newUser = userUtils.createNewUser(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
        return newUser;
	}

    @GetMapping("/regitrationConfirm")
    public ApplicationUser confirmRegistration(@RequestParam("token") String token) {
        LOGGER.info("Activate user account by verification token");
        VerificationToken verificationToken = userUtils.getVerificationTokenByToken(token);
        if (verificationToken == null) {
            throw new TokenNotExistException("Verification link is invalid. Please check correct copy of verification token at the end of the URL link.", PPlatformErrorCodes.TOKEN_NOT_EXIST.getErrorCode());
        }

        ApplicationUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new TokenExpiredException("Activation link is expired after 24 hours. To register please contact support.", PPlatformErrorCodes.TOKEN_EXPIRED.getErrorCode());
        }

        user.setEnabled(true);
        return userUtils.updatePlatformUser(user);
    }


}
