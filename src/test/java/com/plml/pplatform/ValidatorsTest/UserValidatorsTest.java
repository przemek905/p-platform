package com.plml.pplatform.ValidatorsTest;

import com.plml.pplatform.H2JpaConfig;
import com.plml.pplatform.PPlatformApplication;
import com.plml.pplatform.Users.ApplicationUser;
import com.plml.pplatform.Users.UserPlatformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PPlatformApplication.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class UserValidatorsTest {

    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private Validator validator;

    @Test
    public void shouldValidateDuplicatedLogin() throws Exception {
        // given
        String login = "testuser";
        ApplicationUser predefinedUser = new ApplicationUser(1, login, "testpassword", "testmail@vp.pl", "test");
        userPlatformService.save(predefinedUser);
        // when
        ApplicationUser newUser = new ApplicationUser(1, login, "anothertestpassword2", "anothertestmail2@vp.pl", "test");
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(newUser);
        // then
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), "User with that nickname already exist in database");
        assertEquals(violations.iterator().next().getConstraintDescriptor().getConstraintValidatorClasses().get(0).getSimpleName(), "UserExistValidator");
    }

    @Test
    public void shouldValidateDuplicatedEmail() throws Exception {
        // given
        String email = "testuser@pp.pl";
        ApplicationUser predefinedUser = new ApplicationUser(1, "user", "testpassword", email, "test");
        userPlatformService.save(predefinedUser);
        // when
        ApplicationUser newUser = new ApplicationUser(1, "newUser", "anothertestpassword2", email, "test");
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(newUser);
        // then
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), "This email address already exist in database");
        assertEquals(violations.iterator().next().getConstraintDescriptor().getConstraintValidatorClasses().get(0).getSimpleName(), "EmailExistValidator");
    }

}
