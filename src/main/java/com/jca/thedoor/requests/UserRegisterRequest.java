package com.jca.thedoor.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterRequest {

    private String id;
    @NotBlank(message = "fullName cannot be empty")
    @NotNull(message = "fullName is required")
    private String fullName;

    @NotBlank(message = "Email cannot be empty")
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "cellPhoneNumber cannot be empty")
    @NotNull(message = "cellPhoneNumber is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String cellPhoneNumber;

    @NotNull(message = "codCountry  is required")
    @NotBlank(message = "codCountry cannot be empty")
    private String codCountry;

    private String tokenExchange;

    //Authentication
    @NotBlank(message = "Username cannot be empty")
    @NotNull(message = "Username is required")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "idRoles is required")
    private String[] idRoles;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }
    public void setCellPhoneNumber(String cellPhoneNumber) { this.cellPhoneNumber = cellPhoneNumber; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String[] getIdRoles() { return idRoles; }
    public void setIdRoles(String[] idRoles) { this.idRoles = idRoles; }

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
