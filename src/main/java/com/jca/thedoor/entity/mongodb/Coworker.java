package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Document("coworkers")
public class Coworker {
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
    @Indexed(unique = true)
    private String name;

    private String alias;

    @NotNull
    @NotBlank
    private String role;

    @NotNull
    @NotBlank
    @Indexed(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String country;

    @Indexed(unique = true)
    private String cellPhoneNumber;

    private String group;

    @NotNull
    private Date creationDate;

    private Coworker() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public static class Builder {
        private final Coworker coworker;

        public Builder() {
            coworker = new Coworker();
        }

        public Builder id(ObjectId _id) {
            coworker._id = _id;
            return this;
        }

        public Builder id(String id) {
            coworker.id = id;
            return this;
        }

        public Builder user(String user) {
            coworker.user = user;
            return this;
        }

        public Builder notebook(String notebook) {
            coworker.notebook = notebook;
            return this;
        }

        public Builder name(String name) {
            coworker.name = name;
            return this;
        }

        public Builder alias(String alias) {
            coworker.alias = alias;
            return this;
        }

        public Builder role(String role) {
            coworker.role = role;
            return this;
        }

        public Builder email(String email) {
            coworker.email = email;
            return this;
        }

        public Builder country(String country) {
            coworker.country = country;
            return this;
        }

        public Builder cellPhoneNumber(String cellPhoneNumber) {
            coworker.cellPhoneNumber = cellPhoneNumber;
            return this;
        }

        public Builder group(String group) {
            coworker.group = group;
            return this;
        }

        public Builder creationDate(Date creationDate) {
            coworker.creationDate = creationDate;
            return this;
        }

        public Coworker build() {
            return coworker;
        }
    }

    /*Coworker coworker = new Coworker.Builder()
            .id(new ObjectId())
            .user(#user)
            .notebook("Notebook")
            .name("John Doe")
            .alias("John")
            .role("Role")
            .email("john.doe@example.com")
            .country("Country")
            .cellPhoneNumber("123-456-7890")
            .group("Group")
            .creationDate(new Date())
            .build();*/
}