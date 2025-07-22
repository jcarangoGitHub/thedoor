package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;

@Document("users")
public class User {

    private @MongoId ObjectId _id;

    private String id;

    private String fullName;

    @NotNull
    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Indexed(unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String cellPhoneNumber;

    private String codCountry;

    private String tokenExchange;

    public User() {
        super();
    }

    public User(ObjectId _id, String fullName, String email, String cellPhoneNumber,
                String codCountry, String tokenExchange) {
        this._id = _id;
        this.fullName = fullName;
        this.email = email;
        this.cellPhoneNumber = cellPhoneNumber;
        this.codCountry = codCountry;
        this.tokenExchange = tokenExchange;
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

    public String getCodCountry() {
        return codCountry;
    }

    public void setCodCountry(String codCountry) {
        this.codCountry = codCountry;
    }

    public String getTokenExchange() {
        return tokenExchange;
    }

    public void setTokenExchange(String tokenExchange) {
        this.tokenExchange = tokenExchange;
    }
}

