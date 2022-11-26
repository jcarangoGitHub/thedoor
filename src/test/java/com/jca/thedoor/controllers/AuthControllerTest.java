package com.jca.thedoor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jca.thedoor.TestSecurityConfig;
import com.jca.thedoor.security.jwt.JwtTokenUtil;
import com.jca.thedoor.security.payload.RegisterRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private static ObjectMapper mapper = new ObjectMapper();
    static RegisterRequest _userUnreal;
    static RegisterRequest _userReal;

    @BeforeAll
    static void init() {
        RegisterRequest userUnreal = new RegisterRequest();
        userUnreal.setUserName("norman3");
        userUnreal.setPassword("1312");
        userUnreal.setEmail("email@email.com");
        userUnreal.setCellPhoneNumber("3030202");
        userUnreal.setRoles(new String[] {""});

        _userUnreal = userUnreal;

        RegisterRequest userReal = new RegisterRequest();
        userReal.setUserName("norman");
        userReal.setPassword("1312");
        userReal.setEmail("email@email.com");
        userReal.setCellPhoneNumber("3030202");
        userReal.setRoles(new String[] {""});

        _userReal = userReal;
    }

    @DisplayName("Authenticate throws exception must returns forbidden status")
    @Test
    public void loginWhenAuthenticateThrowExceptionMustReturnForbiddenStatus() throws Exception {
        Mockito.when(authManager.authenticate(any())).thenThrow(UsernameNotFoundException.class);

        String json = mapper.writeValueAsString(_userUnreal);
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();
    }

    @DisplayName("Search bd. Authenticate must returns ok status and token")
    @Tag("bd")
    @Test
    public void loginWhenAuthenticateMustReturnOkStatusAndToken_bd() throws Exception {
        Mockito.when(authManager.authenticate(any())).thenReturn(any());

        String json = mapper.writeValueAsString(_userReal);
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resContent = result.getResponse().getContentAsString();
        Assert.assertTrue(resContent.contains("token"));
    }


}
