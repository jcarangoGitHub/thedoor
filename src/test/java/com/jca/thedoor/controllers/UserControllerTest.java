package com.jca.thedoor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jca.thedoor.TestSecurityConfig;
import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.mocks.RegisterRequestFake;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authManager;

    /*@MockBean
    private UserRepository userRepository;*/

    /*@MockBean
    private PasswordEncoder passwordEncoder;*/

    private static ObjectMapper mapper = new ObjectMapper();
    private static User _user;
    private static RegisterRequestFake _userFake;
    private String _json;
    private final static String FIELDS_ALREADY_EXISTS =
            "No es posible guardar el registro. Campo duplicado:";
    private String _token;

    @BeforeAll
    static void init() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setEmail("test@email.com");
        user.setCellPhoneNumber("3030202");
        user.setRoles(Arrays.stream(new String[] {"638bed3b305b5b29288a1e51"}).toList());

        _user = user;
    }

    private ResultActions performRegisterMockMvc() throws Exception {
        return mockMvc.perform(post("/api/user/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(_json).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions performUpdateMockMvc() throws Exception {
        return mockMvc.perform(put("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(_json).accept(MediaType.APPLICATION_JSON));
    }


    @DisplayName("REPLACE")
    @Test
    @Order(1)
    public void replace() throws Exception {
        _json = mapper.writeValueAsString("");

        //MvcResult result = performReplaceMockMvc().andExpect(status().isOk()).andReturn();
        //System.out.println(result.getResponse());
    }


    //<editor-fold desc="/api/user/register">
    @DisplayName("1. /api/user/register. Register with username null must return error and msg")

    @Test
    @Order(1)
    public void registerWithUserNameNullMustReturnStatusBadRequestAndMsg() throws Exception {
        final String usernameMissing = "Field error in object 'user' on field 'username': rejected value [null]";
        String temp = _user.getUsername();
        _user.setUsername(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(usernameMissing));

        _user.setUsername(temp);
    }

    @DisplayName("2. /api/user/register. Register with username empty must return status 400 " +
            "bad request and msg")
    @Test
    @Order(2)
    public void registerWithUserNameEmptyMustReturnStatusBadRequestAndMsg() throws Exception {
        final String usernameEmpty = "must not be blank";

        String temp = _user.getUsername();
        _user.setUsername("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(usernameEmpty));
        Assert.assertTrue(errorMessage.contains("username"));

        _user.setUsername(temp);
    }

    @DisplayName("3. /api/user/register. Register with email null must return bad request status and msg")
    @Test
    @Order(3)
    public void registerWithEmailNullMustReturnBadRequestStatusAndMsg() throws Exception {
        final String emailMissing = "Field error in object 'user' on field 'email': rejected value";
        String temp = _user.getEmail();
        _user.setEmail(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(emailMissing));

        _user.setEmail(temp);
    }

    @DisplayName("4. /api/user/register. Register with email empty must return bad request status and msg")
    @Test
    @Order(4)
    public void registerWithEmailEmptyMustReturnBadRequestStatusAndMsg() throws Exception {
        final String emailEmpty = "must not be blank";

        String temp = _user.getEmail();
        _user.setEmail("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(emailEmpty));
        Assert.assertTrue(errorMessage.contains("email"));

        _user.setEmail(temp);
    }

    @DisplayName("5. /api/user/register. Register without email format must returns status " +
            "400 bad request and msg")
    @Test
    @Order(5)
    public void registerWithoutEmailFormatMustReturnsBadRequestStatusAndMsg() throws Exception {
        final String emailEmpty = "Field error in object 'user' on field 'email'";

        String temp = _user.getEmail();
        _user.setEmail("xxx");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(emailEmpty));

        _user.setEmail(temp);
    }

    @DisplayName("6. /api/user/register. Register with password null must returns bad request status and msg")
    @Test
    @Order(6)
    public void registerWithPasswordNullMustReturnBadRequestStatusAndMsg() throws Exception {
        final String passwordNull = "Field error in object 'user' on field 'password': rejected value [null]";
        String temp = _user.getPassword();
        _user.setPassword(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(passwordNull));

        _user.setPassword(temp);
    }

    @DisplayName("7. /api/user/register. Register with password empty must return 400 and msg")
    @Test
    @Order(7)
    public void registerWithPasswordEmptyMustReturnStatusBadRequestAndMsg() throws Exception {
        final String emailEmpty = "must not be blank";

        String temp = _user.getPassword();
        _user.setPassword("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(emailEmpty));
        Assert.assertTrue(errorMessage.contains("password"));

        _user.setPassword(temp);
    }

    @BeforeEach
    void instanceUserFake() {
        RegisterRequestFake userFake = new RegisterRequestFake();
        userFake.setUserName("norman");
        userFake.setPassword("1312");
        userFake.setEmail("email@email.com");
        userFake.setCellPhoneNumber("3030202");

        _userFake = userFake;
    }

    @DisplayName("8. /api/user/register. Register with roles null must return status 400 bad request and msg")
    @Test
    @Order(8)
    public void registerWithRoleNullMustReturnStatusBadRequestAndMsg() throws Exception {
        final String rolesMissing = "Field error in object 'user' on field 'roles': rejected value [null]";

        _json = mapper.writeValueAsString(_userFake);
        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(rolesMissing));
    }

    @DisplayName("9. /api/user/register. Register with roles empty must return status 400 bad request and msg")
    @Test
    @Order(9)
    public void registerWithRoleEmptyMustReturnStatusBadRequestAndMsg() throws Exception {
        final String rolesEmpty = "must not be empty";

        List<String> temp = _user.getRoles();
        _user.setRoles(Arrays.stream(new String[] {}).toList());
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(rolesEmpty));
        Assert.assertTrue(errorMessage.contains("roles"));

        _user.setRoles(temp);
    }

    @DisplayName("10. /api/user/register. Register with bad role must return not found status and msg")
    @Test
    @Order(10)
    public void registerWithBadRoleMustReturnNotFoundStatusAndMsg() throws Exception {
        final String badId = "636331a5cf4c10c00f5b3ef2";
        final String roleNotFound = "No es posible guardar el registro. Rol no existe: " + badId;

        List<String> temp = _user.getRoles();
        _user.setRoles(Arrays.stream(new String[] {badId}).toList());
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isNotFound()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(roleNotFound));

        _user.setRoles(temp);
    }

    @DisplayName("11. /api/user/register. Successful must return status ok")
    @Test
    @Order(11)
    public void registerUserMustReturnStatusOkAndReturnIsNotEmpty() throws Exception {
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isOk()).andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @DisplayName("12. /api/user/register. When username exists must return status conflict and msg")
    @Test
    @Order(12)
    public void registerWhenUserNameExistsMustReturnStatusConflictAndMsg() throws Exception {
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("username"));
    }

    @DisplayName("13. /api/user/register. When email exists must return status conflict and msg")
    @Test
    @Order(13)
    public void registerWhenEmailExistsMustReturnStatusConflictAndMsg() throws Exception {
        String temp = _user.getUsername();
        _user.setUsername("othertest");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performRegisterMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResolvedException().getMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("email"));

        _user.setUsername(temp);
    }

    @DisplayName("14. /api/user/register. When cell phone number exists must return status conflict and msg")
    @Test
    @Order(14)
    public void registerWhenCellPhoneNumberExistsMustReturnStatusConflictAndMsg() throws Exception {
        String tempUsername = _user.getUsername();
        String tempEmail = _user.getEmail();
        _user.setUsername("othertest");
        _user.setEmail("other@email.com");

        _json = mapper.writeValueAsString(_user);


        MvcResult result = performRegisterMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("cellPhoneNumber"));

        _user.setUsername(tempUsername);
        _user.setEmail(tempEmail);
    }
    //</editor-fold>

    //<editor-fold desc="/api/user/update">
    @DisplayName("15 /api/user/update update when id is null must return bad request status and msg")
    @Test
    @Order(15)
    public void updateWhenIdIsNullMustReturnBadRquestStatusAndMsg() throws Exception {
        final String updateIdNotNull = "No es posible actualizar el registro. Id: no debe ser nulo";

        _json = mapper.writeValueAsString(_user);

        MvcResult result = performUpdateMockMvc().andExpect(status().isBadRequest())
                .andReturn();

        String errorMessage = result.getResponse().getErrorMessage();


        Assert.assertEquals(errorMessage,updateIdNotNull);

    }

    @DisplayName("16 /api/user/update when id is not null and valid and userName null must return status bad request")
    @Test
    @Order(16)
    public void updateWhenIdIsNotNullAndValidAndUsernameNullMustReturnStatusBadRequest() throws Exception {
        String usernameNull = "Field error in object 'user' on field 'username': rejected value [null]";
        String temp = _user.getUsername();
        _user.setId(new ObjectId());
        _user.setUsername(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performUpdateMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errMsg = result.getResolvedException().getMessage();

        _user.setUsername(temp);

        Assert.assertTrue(errMsg.contains(usernameNull));//because @Valid
    }

    @DisplayName("17 /api/user/update when id is not null and valid and password is null must " +
            "returns bad request status")
    @Test
    @Order(17)
    public void updateWhenIdIsNotNullAndPasswordNullMustReturnBadRequestStatus() throws Exception {
        final String passwordNull = "Field error in object 'user' on field 'password': rejected value [null]";

        String temp = _user.getPassword();

        _user.setId(new ObjectId());
        _user.setPassword(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performUpdateMockMvc().andExpect(status().isBadRequest()).andReturn();
        String errMsg = result.getResolvedException().getMessage();

        Assert.assertTrue(errMsg.contains(passwordNull));

        _user.setPassword(temp);
    }

    @DisplayName("18 /api/user/update when id is not null and valid and id doesn't exist " +
            "returns status not found and msg")
    @Test
    @Order(18)
    public void updateWhenIdIsNotNullAndIdDoesntExistMustReturnStatusNotFoundAndMsg() throws Exception {
        _user.setId(new ObjectId());
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performUpdateMockMvc().andExpect(status().isNotFound()).andReturn();

        String msg = "Id no encontrado.";
        Assert.assertEquals(msg, result.getResponse().getErrorMessage());
    }

    @DisplayName("19 /api/auth/login")
    @Test
    @Order(19)
    public void loginWhenAuthenticateMustReturnOkStatusAndToken_bd() throws Exception {
        Mockito.when(authManager.authenticate(any())).thenReturn(any());

        String json = mapper.writeValueAsString(_user);
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resContent = result.getResponse().getContentAsString();
        Assert.assertTrue(resContent.contains("token"));
        _token = resContent.split(":")[1].replace("\"", "").replace("}", "");
        System.out.println(_token);
    }


    @DisplayName("20 /api/user/update when id is not null and valid must return status ok")
    @Test
    @Order(20)
    public void findByUserNameWhenIdIsNotNullMustReturnStatusOk() throws Exception {
        //_user.setId(new ObjectId());
        _json = mapper.writeValueAsString(_user);

        _token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjcwNzg3MjE5LCJleHAiOjE2NzA3ODgwODN9.HBRm1K7WChLrjTsc0648fUebJUx4KAjv_IHV0mq-Dj-zBOQ2f9cHFb9g-OpimqwoB8nxjErggWawwO7h81m7_g";

        MvcResult result = mockMvc.perform(
                get("/api/user/findByUserName")
                        .header("authentication", "Bearer " + _token)
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(_json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
                        /*.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(_json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();*/
        System.out.println(result);
    }

    @DisplayName("16 /api/user/update when id is not null and valid must return status ok")
    @Test
    @Order(16)
    public void updateWhenIdIsNotNullMustReturnStatusOk() throws Exception {
        _user.setId(new ObjectId());
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performUpdateMockMvc().andExpect(status().isOk()).andReturn();
    }



    @DisplayName("19 /api/user/update when id is not null and valid and password updated must " +
            "returns status not allowed and msg")
    @Test
    @Order(19)
    public void updateWhenIdIsNotNullAndPasswordUpdatedMustReturnStatusNotAllowedAndMsg() throws Exception {
        _user.setId(new ObjectId());

        User u = new User("test2", "m@m.c", "3030201");
        u.setId(new ObjectId());
        u.setPassword("password_changed");
        u.setRoles(Arrays.stream(new String[] {"ROLE_"}).toList());

        _json = mapper.writeValueAsString(u);

        MvcResult result = performUpdateMockMvc().andExpect(status().isMethodNotAllowed()).andReturn();
        String msg = "No es posible cambiar la contraseña. \n" +
        "Dirigase a 'restablecer contraseña'";
        Assert.assertEquals(msg, result.getResponse().getErrorMessage());
    }


    //</editor-fold>

}
