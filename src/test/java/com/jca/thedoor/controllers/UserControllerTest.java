package com.jca.thedoor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jca.thedoor.TestSecurityConfig;
import com.jca.thedoor.mocks.RegisterRequestFake;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.security.payload.RegisterRequest;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static ObjectMapper mapper = new ObjectMapper();
    private static RegisterRequest _user;
    private static RegisterRequestFake _userFake;
    private String _json;
    private final static String FIELDS_ALREADY_EXISTS =
            "Verificar. Al menos uno de los siguientes campos ya se encuentra registrado:";

    @BeforeAll
    static void init() {
        RegisterRequest user = new RegisterRequest();
        user.setUserName("norman");
        user.setPassword("1312");
        user.setEmail("email@email.com");
        user.setCellPhoneNumber("3030202");
        user.setRoles(new String[] {""});

        _user = user;
    }

    private ResultActions performMockMvc() throws Exception {
        return mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(_json).accept(MediaType.APPLICATION_JSON));
    }

    @DisplayName("1. /api/user/register. Register with username null must return 422 and msg")
    @Test
    @Order(1)
    public void registerWithUserNameNullMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getUserName();
        _user.setUserName(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setUserName(temp);
    }

    @DisplayName("2. /api/user/register. Register with username empty must return 422 and msg")
    @Test
    @Order(2)
    public void registerWithUserNameEmptyMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getUserName();
        _user.setUserName("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setUserName(temp);
    }

    @DisplayName("3. /api/user/register. Register with email null must return 422 and msg")
    @Test
    @Order(3)
    public void registerWithEmailNullMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getEmail();
        _user.setEmail(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setEmail(temp);
    }

    @DisplayName("4. /api/user/register. Register with email empty must return 422 and msg")
    @Test
    @Order(4)
    public void registerWithEmailEmptyMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getEmail();
        _user.setEmail("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setEmail(temp);
    }

    @DisplayName("5. /api/user/register. Register with cell phone null must return 422 and msg")
    @Test
    @Order(5)
    public void registerWithCellPhoneNullMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getCellPhoneNumber();
        _user.setCellPhoneNumber(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setCellPhoneNumber(temp);
    }

    @DisplayName("6. /api/user/register. Register with cell phone empty must return 422 and msg")
    @Test
    @Order(6)
    public void registerWithCellPhoneEmptyMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getCellPhoneNumber();
        _user.setCellPhoneNumber("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setCellPhoneNumber(temp);
    }

    @DisplayName("7. /api/user/register. Register with password null must return 422 and msg")
    @Test
    @Order(7)
    public void registerWithPasswordNullMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getPassword();
        _user.setPassword(null);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setPassword(temp);
    }

    @DisplayName("8. /api/user/register. Register with password empty must return 422 and msg")
    @Test
    @Order(8)
    public void registerWithPasswordEmptyMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String temp = _user.getPassword();
        _user.setPassword("");
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

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

    @DisplayName("9. /api/user/register. Register with roles null must return 422 and msg")
    @Test
    @Order(9)
    public void registerWithRoleNullMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        _json = mapper.writeValueAsString(_userFake);
        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");
    }

    @DisplayName("10. /api/user/register. Register with roles empty must return 422 and msg")
    @Test
    @Order(10)
    public void registerWithRoleEmptyMustReturnStatusUnprocessableEntityAndMsg() throws Exception {
        String[] temp = _user.getRoles();
        _user.setRoles(new String[]{});
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isUnprocessableEntity()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertEquals(errorMessage, "Faltan campos necesarios para registrar el usuario");

        _user.setRoles(temp);
    }

    @DisplayName("11. /api/user/register. When username exists must return status conflict and msg")
    @Test
    @Order(11)
    public void registerWhenUserNameExistsMustReturnStatusConflictAndMsg() throws Exception {
        Mockito.when(userRepository.findFirstByUserNameExists(Mockito.anyString())).thenReturn(true);
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("Nombre de usuario"));
    }

    @BeforeEach
    void before() {
        Mockito.when(userRepository.findFirstByUserNameExists(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
    }

    @DisplayName("12. /api/user/register. When email exists must return status conflict and msg")
    @Test
    @Order(12)
    public void registerWhenEmailExistsMustReturnStatusConflictAndMsg() throws Exception {
        _json = mapper.writeValueAsString(_user);

        MvcResult result = performMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("Email"));
    }

    @DisplayName("13. /api/user/register. When cell phone number exists must return status conflict and msg")
    @Test
    @Order(13)
    public void registerWhenCellPhoneNumberExistsMustReturnStatusConflictAndMsg() throws Exception {
        _json = mapper.writeValueAsString(_user);
        Mockito.when(userRepository.findFirstByUserNameExists(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByCellPhoneNumber(Mockito.anyString())).thenReturn(true);

        MvcResult result = performMockMvc().andExpect(status().isConflict()).andReturn();
        String errorMessage = result.getResponse().getErrorMessage();

        Assert.assertTrue(errorMessage.contains(FIELDS_ALREADY_EXISTS));
        Assert.assertTrue(errorMessage.contains("Número celular"));
    }

    @DisplayName("14. /api/user/register. Successful must return status ok")
    @Test
    @Order(14)
    public void registerUserMustReturnStatusOk() throws Exception {
        _json = mapper.writeValueAsString(_user);
        Mockito.when(userRepository.findFirstByUserNameExists(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByCellPhoneNumber(Mockito.anyString())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(any())).thenReturn(Mockito.anyString());

        performMockMvc().andExpect(status().isOk());
    }

}
