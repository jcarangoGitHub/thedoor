package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class MongoModel {
    @MongoId
    private ObjectId _id;
    private String id;
    public void assignStringId() {
        this.setId(this.get_id().toString());
    }

    // Getters and Setters

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
