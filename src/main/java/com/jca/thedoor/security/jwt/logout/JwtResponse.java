package com.jca.thedoor.security.jwt.logout;

public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiryDuration;
}
