package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("thoughts")
public class Thought {
    @MongoId
    private ObjectId _id;
    private String id;

    @NotNull
    @NotBlank
    private String user;

    @NotNull
    @NotBlank
    private String notebook;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    @NotBlank
    private Date creationDate;

    private Date startDate;
    private Date endDate;

    private Thought() {
        super();
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static class Builder {
        private final Thought thought;

        public Builder() {
            thought = new Thought();
        }

        public Builder id(ObjectId _id) {
            thought._id = _id;
            return this;
        }

        public Builder id(String id) {
            thought.id = id;
            return this;
        }

        public Builder user(String user) {
            thought.user = user;
            return this;
        }

        public Builder notebook(String notebook) {
            thought.notebook = notebook;
            return this;
        }

        public Builder type(String type) {
            thought.type = type;
            return this;
        }

        public Builder description(String description) {
            thought.description = description;
            return this;
        }

        public Builder status(String status) {
            thought.status = status;
            return this;
        }

        public Builder creationDate(Date creationDate) {
            thought.creationDate = creationDate;
            return this;
        }

        public Builder startDate(Date startDate) {
            thought.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            thought.endDate = endDate;
            return this;
        }

        public Thought build() {
            return thought;
        }
    }
}
    /*Thought thought = new Thought.Builder()
            .id(new ObjectId())
            .user("John Doe")
            .type("Type")
            .description("Description")
            .status("Status")
            .creationDate(new Date())
            .startDate(new Date())
            .endDate(new Date())
            .build();*/