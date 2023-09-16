package com.jca.thedoor.exception;

public class UtilException {
    public static String EXTRACT_DUPLICATE_MESSAGE_FROM_EXCEPTION(String exceptionMessage) {
        String startText = "index: ";
        int startIndex = exceptionMessage.indexOf(startText);

        if (startIndex != -1) {
            int endIndex = exceptionMessage.indexOf("\" }", startIndex + startText.length());
            if (endIndex != -1) {
                String keyPart = exceptionMessage.substring(startIndex, endIndex + 3);
                return keyPart;
            }
        }

        return null; // Return null if the desired part is not found in the exception message.
    }
}
