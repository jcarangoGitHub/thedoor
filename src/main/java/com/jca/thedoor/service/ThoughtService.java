package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Thought;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ThoughtService {
    ResponseEntity<Thought> createThought(Thought thought);

    ResponseEntity<List<Thought>> findThoughtsByNotebook(String user, String notebook);
    static String extractDuplicateExceptionMessage(String exceptionMessage) {
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
