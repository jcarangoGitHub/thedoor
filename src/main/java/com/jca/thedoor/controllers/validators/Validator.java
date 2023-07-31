package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.repository.mongodb.UserRepository;
import org.bson.types.ObjectId;

public class Validator {
    protected boolean isValidObjectId(ObjectId id) {
        return ObjectId.isValid(id.toString());
    }

    protected void validateUser(UserRepository userRepository, String userId) {
        validateId(userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("notebook.user - user not found");
        }
    }

    public static void validateId(String id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }
        if (!id.matches("^[0-9a-fA-F]{24}$")) { // The regular expression assumes that the ObjectId is a
            // 24-character hexadecimal string, which is the standard format for MongoDB ObjectIds.
            throw new BadRequestException("The provided ID is not a valid MongoDB ObjectId");
        }
    }

}
