package com.jca.thedoor.security.jwt.logout;

import com.jca.thedoor.security.jwt.JwtTokenUtil;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class LoggedOutJwtTokenCache {

    private ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;

    private JwtTokenUtil tokenProvider;

    @Autowired
    public LoggedOutJwtTokenCache(JwtTokenUtil tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(1000)
                .build();
    }

    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
        String token = event.getToken();
        if (tokenEventMap.containsKey(token)) {
            //logger.info(String.format("Log out token for user [%s] is already present in the cache", event.getUserEmail()));
            System.out.println("Log out token for user [%s] is already present in the cache" +  event.getUserEmail());

        } else {
            Date tokenExpiryDate = tokenProvider.getTokenExpiryFromJWT(token);
            long ttlForToken = getTTLForToken(tokenExpiryDate);
            //logger.info(String.format("Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s]", event.getUserEmail(), ttlForToken, tokenExpiryDate));
            System.out.println("Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s] " +  event.getUserEmail() + "-" + ttlForToken + "-" + tokenExpiryDate);
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
        return tokenEventMap.get(token);
    }

    private long getTTLForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }
}
