package com.jca.thedoor.controllers.validators;

import org.bson.types.ObjectId;

public class Validator {
    boolean isValidObjectId(ObjectId id) {
        return ObjectId.isValid(id.toString());
    }
}
