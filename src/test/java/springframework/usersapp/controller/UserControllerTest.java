package springframework.usersapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import springframework.usersapp.model.User;
import springframework.usersapp.payload.UserDto;
import springframework.usersapp.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setEmail("john-doe@text.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate("1998 02 21");
    }

    @Test
    public void testCreateUser() throws Exception{

        given(userService.createUser(mapToDto(user)))
                .willAnswer((invocation)-> invocation.getArgument(0));

       mockMvc.perform(post("/POST/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
               .andDo(print()).
                andExpect(status().isCreated())
                .andExpect( jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect( jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect( jsonPath("$.email",
                        is(user.getEmail())));
;
    }

    @Test
    void testUpdateSomeUserFields() throws Exception {
        long userId = 1L;
        Map<String, Object> fields = Map.of(
                "firstName", "Jill",
                "lastName", "Putch"
        );
        User updatedUser = new User();
        updatedUser.setFirstName((String) fields.get("firstName"));
        updatedUser.setLastName((String) fields.get("lastName"));
        updatedUser.setEmail(user.getEmail());


        given(userService.getById(userId)).willReturn(mapToDto(user));
        given(userService.updateSomeUserFields(userId, fields))
                .willReturn(mapToDto(updatedUser));

        mockMvc.perform(patch("/PATCH/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk());

    }


    @Test
    void testUpdateUser() throws Exception {

        long userId = 1L;

        User updatedUser = new User();
        updatedUser.setEmail("jill34@text.com");
        updatedUser.setFirstName("Jill");
        updatedUser.setLastName("Dumpf");
        updatedUser.setBirthDate("1987 12 01");
        given(userService.getById(userId)).willReturn(mapToDto(user));
        given(userService.updateUser(mapToDto(updatedUser), userId))
                .willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/PUT/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andDo(print()).
                andExpect(status().isOk())
                .andExpect( jsonPath("$.firstName",
                        is(updatedUser.getFirstName())))
                .andExpect( jsonPath("$.lastName",
                        is(updatedUser.getLastName())))
                .andExpect( jsonPath("$.email",
                        is(updatedUser.getEmail())));
    }

    @Test
    public void testDeleteUser() throws Exception{

        long userId = 1L;
        willDoNothing().given(userService).deleteUserById(userId);

       mockMvc.perform(delete("/DELETE/users/{id}", userId))
               .andExpect(status().isOk());
    }

    private static UserDto mapToDto(User newUser) {
        UserDto userResponse = new UserDto();
        userResponse.setId(newUser.getId());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setBirthDate(newUser.getBirthDate());
        userResponse.setAddress(newUser.getAddress());
        userResponse.setPhoneNumber(newUser.getPhoneNumber());
        return userResponse;
    }

}