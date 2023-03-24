package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Document//(collation = "user")
public class User {

    private @MongoId ObjectId _id;

    private String id;

    private String fullName;

    @NotNull
    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String cellPhoneNumber;
    private Set<UserRole> userRoles;

    private String tokenExchange;

    public User() {
        super();
    }

    public User(ObjectId _id, String email, String cellPhoneNumber) {
        super();
        this._id = _id;
        this.id = _id.toString();
        this.email = email;
        this.cellPhoneNumber = cellPhoneNumber;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getTokenExchange() {
        return tokenExchange;
    }

    public void setTokenExchange(String tokenExchange) {
        this.tokenExchange = tokenExchange;
    }
}

