package com.jca.thedoor.security.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import com.jca.thedoor.entity.mongodb.User;

public class JwtResponse {
    @Schema(description = "Generated JWT token")
    private String token;
    @Schema(description = "Authenticated user information")
    private User user;

    public JwtResponse() {
    }
    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

