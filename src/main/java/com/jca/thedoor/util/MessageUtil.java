package com.jca.thedoor.util;

import java.util.Arrays;

public class MessageUtil {

    final static String FIELDS_ALREADY_EXISTS =
            "Verificar. Al menos uno de los siguientes campos ya se encuentra registrado:";

    public static String getMessageFieldsAlreadyExists(String[] fields) {
        StringBuilder stringBuilder = new StringBuilder(FIELDS_ALREADY_EXISTS);
        stringBuilder.append(System.getProperty("line.separator"));
        Arrays.stream(fields).toList().forEach(field -> {
            stringBuilder.append(field);
            stringBuilder.append(System.getProperty("line.separator"));
        });
        return stringBuilder.toString();
    }
}
