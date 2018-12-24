package com.plml.pplatform.UsersTest;

import com.plml.pplatform.H2JpaConfig;
import com.plml.pplatform.PPlatformApplication;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.UserRepository;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationToken;
import com.plml.pplatform.users.signOnProcess.verificationtoken.VerificationTokenRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PPlatformApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationTest {

    private static final String VALID_TOKEN = "valid_token";
    private static final String INVALID_TOKEN = "invalid_token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Before
    public void createUserAndTokenInPlatform() {
            ApplicationUser user = new ApplicationUser(1, "testuser", "testuserpass", "testmail@vp.pl", "test");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            VerificationToken verificationToken = createVerificationToken(user, VALID_TOKEN);
            verificationTokenRepository.save(verificationToken);
    }

    @After
    public void cleanDB() {
        verificationTokenRepository.deleteAll();
    }

    @Test
    public void shouldSuccessRegister() throws Exception {
        //when
        this.mockMvc.perform(get("/registrationConfirm")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testuser")))
                .andExpect(content().string(containsString("\"enabled\":true")));
    }

    @Test
    public void shouldNotRegisterUserWithInvalidToken() throws Exception {
        //when
        this.mockMvc.perform(get("/registrationConfirm")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", INVALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Verification link is invalid. Please check correct copy " +
                        "of verification token at the end of the URL link.")));
    }

    @Test
    public void shouldNotRegisterWhenTokenExpired() throws Exception {
        //given
        VerificationToken token = verificationTokenRepository.findByToken(VALID_TOKEN);
        token.setExpiryDate(DateUtils.setDays(new Date(), 12));
        verificationTokenRepository.save(token);

        //when
        this.mockMvc.perform(get("/registrationConfirm")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Activation link is expired after 24 hours. To register please contact support")));

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
