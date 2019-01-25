package com.plml.pplatform.UsersTest;

import com.plml.pplatform.H2JpaConfig;
import com.plml.pplatform.PPlatformApplication;
import com.plml.pplatform.TestUtils.TestUtils;
import com.plml.pplatform.users.ApplicationUser;
import com.plml.pplatform.users.UserPlatformService;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PPlatformApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdatePasswordTest {

    private static final String VALID_TEST_PASSWORD = "testpassword";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String INVAID_PASSWORD = "invaid_password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPlatformService userPlatformService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void createUserInPlatform() {
        ApplicationUser user = new ApplicationUser(1, "testuser", VALID_TEST_PASSWORD, "testmail@vp.pl", "test");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userPlatformService.saveUser(user);
    }

    @Test
    public void shouldUpdatePasswordWhenLogged() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser(1, "testuser", VALID_TEST_PASSWORD, "testmail@vp.pl", "test");

        String requestJson = TestUtils.makeJsonFromObject(user);

        //login first
        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(AUTHORIZATION_HEADER))
                .andExpect(header().string(AUTHORIZATION_HEADER, containsString("Bearer")))
                .andReturn();

        //get authorization header
        String authorizationToken = result.getResponse().getHeader(AUTHORIZATION_HEADER);

        //when
        this.mockMvc.perform(get("/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + authorizationToken)
                .param("password", NEW_PASSWORD)
                .param("oldPassword", VALID_TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Password updated")));
    }

    @Test
    public void shouldNotUpdatePasswordWhenOldPasswordInvalid() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser(1, "testuser", VALID_TEST_PASSWORD, "testmail@vp.pl", "test");

        String requestJson = TestUtils.makeJsonFromObject(user);

        //login first
        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(AUTHORIZATION_HEADER))
                .andExpect(header().string(AUTHORIZATION_HEADER, containsString("Bearer")))
                .andReturn();

        //get authorization header
        String authorizationToken = result.getResponse().getHeader(AUTHORIZATION_HEADER);

        //when
        this.mockMvc.perform(get("/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + authorizationToken)
                .param("password", NEW_PASSWORD)
                .param("oldPassword", INVAID_PASSWORD))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Old password is incorrect")));
    }

    @Test
    public void shouldNotUpdatePasswordWhenNotLogin() throws Exception {

        //when
        this.mockMvc.perform(get("/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .param("password", NEW_PASSWORD)
                .param("oldPassword", INVAID_PASSWORD))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(status().reason(containsString("Access Denied")));
    }
}
