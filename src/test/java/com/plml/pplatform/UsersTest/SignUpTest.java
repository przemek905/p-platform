package com.plml.pplatform.UsersTest;

import com.plml.pplatform.H2JpaConfig;
import com.plml.pplatform.PPlatformApplication;
import com.plml.pplatform.TestUtils.TestUtils;
import com.plml.pplatform.Users.ApplicationUser;
import com.plml.pplatform.Users.UserRepository;
import com.plml.pplatform.Users.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PPlatformApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SignUpTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void shouldSuccefullySignUpNewUser() throws Exception {
        //given
        ApplicationUser newUser = new ApplicationUser(1, "testuser", "testpassword", "testmail@vp.pl", "test");
        when(userService.getUserByUsername(newUser.getUsername())).thenReturn(null);
        when(userService.getUserByEmail(newUser.getEmail())).thenReturn(null);
        when(userService.save(any())).thenReturn(newUser);

        String requestJson = TestUtils.makeJsonFromObject(newUser);

        //when
        this.mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("testuser")));

        //then
        verify(userService, times(1)).save(any());
    }

    @Test
    public void shouldNotSignUpExistingUser() throws Exception {
        //given
        ApplicationUser existingUser = new ApplicationUser(1, "testuser", "testpassword", "testmail@vp.pl", "test");
        when(userService.getUserByUsername(existingUser.getUsername())).thenReturn(existingUser);

        String requestJson = TestUtils.makeJsonFromObject(existingUser);

        thrown.expect(NestedServletException.class);
        thrown.expectMessage(containsString("User already register in platform"));

        //when
        this.mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());
    }

    @Test
    public void shouldNotSignUpUserWithExistingEmail() throws Exception {
        //given
        ApplicationUser userWithExistingEmail = new ApplicationUser(1, "testuser", "testpassword", "testmail@vp.pl", "test");
        when(userService.getUserByUsername(userWithExistingEmail.getUsername())).thenReturn(null);
        when(userService.getUserByEmail(userWithExistingEmail.getEmail())).thenReturn(userWithExistingEmail);

        String requestJson = TestUtils.makeJsonFromObject(userWithExistingEmail);

        thrown.expect(NestedServletException.class);
        thrown.expectMessage(containsString("This email address is already register in platform"));

        //when
        this.mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print());
    }
}
