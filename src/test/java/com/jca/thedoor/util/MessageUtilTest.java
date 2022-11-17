package com.jca.thedoor.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageUtilTest {

    @BeforeAll
    static void init() {
        System.out.println("init......");
    }

    @AfterAll
    static void after() {
        System.out.println("after");
    }

    @BeforeEach
    void before() {
        System.out.println("antes del test actual");
    }

    @AfterEach
    void afterEach() {
        System.out.println("después del test actual");
    }

    @Test
    @DisplayName("test message, fields exist")
    @Tag("develop")
    void getMessageFieldsAlreadyExists() {
        String[] fields = {"Nombre de usuario", "Email"};
        String res = MessageUtil.getMessageFieldsAlreadyExists(fields);
        System.out.println(res);
        String[] lines = res.split("\r\n");

        assertAll(
          "message",
                () -> assertEquals(lines[0], "Verificar. Al menos uno de los siguientes campos ya se encuentra registrado:"),
                () -> assertEquals(lines[1], fields[0]),
                () -> assertEquals(lines[2], fields[1])
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {8, 4})
    void probateParameter(double a) {
        double res = a * 2;
        System.out.println("res: " + res);
        assertEquals(a*2, res);
    }

}