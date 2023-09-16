package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("pulls_request")
public class PullRequest {
    @MongoId
    private ObjectId _id;
    private String id;
    @NotNull
    @NotBlank
    private String thought;
    private String linkRepository;
    @NotNull
    private Date creationDate;

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

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getLinkRepository() {
        return linkRepository;
    }

    public void setLinkRepository(String linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
