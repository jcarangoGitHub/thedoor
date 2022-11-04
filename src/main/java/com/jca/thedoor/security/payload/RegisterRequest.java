package com.jca.thedoor.security.payload;

import com.jca.thedoor.entity.mongodb.User;

import java.util.Arrays;

public class RegisterRequest {
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String[] roles;
    private String cellPhoneNumber;

    public User getUserFromRequest() {
        User user = new User();
        user.setFullName(fullName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(Arrays.stream(roles).toList());
        user.setCellPhoneNumber(cellPhoneNumber);

        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
}
