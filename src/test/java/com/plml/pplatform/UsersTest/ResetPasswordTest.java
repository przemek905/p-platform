package com.plml.pplatform.UsersTest;

import com.plml.pplatform.H2JpaConfig;
import com.plml.pplatform.PPlatformApplication;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.UserPlatformService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PPlatformApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResetPasswordTest {
    private static final String VALID_TEST_PASSWORD = "testpassword";
    public static final String VALID_EMAIL = "testmail@vp.pl";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPlatformService userPlatformService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JavaMailSender mailSender;

    @Before
    public void createUserInPlatform() {
        ApplicationUser user = new ApplicationUser(1, "testuser", VALID_TEST_PASSWORD, VALID_EMAIL, "test", "role");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userPlatformService.saveUser(user);
    }

    @Test
    public void shouldResetPasswordForUser() throws Exception {
        //given
        ApplicationUser user = userPlatformService.getUserByEmail(VALID_EMAIL);
        String password = user.getPassword();

        assertTrue(bCryptPasswordEncoder.matches(VALID_TEST_PASSWORD, password));

        //when
        this.mockMvc.perform(get("/user/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", VALID_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testuser")))
                .andExpect(content().string(containsString("\"passwordReseted\":true")));

        //then
        user = userPlatformService.getUserByEmail(VALID_EMAIL);
        String resetedPassword = user.getPassword();
        assertFalse(bCryptPasswordEncoder.matches(VALID_TEST_PASSWORD, resetedPassword));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

    }
}
