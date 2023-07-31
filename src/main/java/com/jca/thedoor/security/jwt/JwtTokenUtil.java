package com.jca.thedoor.security.jwt;

import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.exception.UnauthorizedException;
import com.jca.thedoor.security.jwt.logout.LoggedOutJwtTokenCache;
import com.jca.thedoor.security.jwt.logout.OnUserLogoutSuccessEvent;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Métodos para generar y validar los token JWT
 */
@Component
public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    @Autowired
    private LoggedOutJwtTokenCache loggedOutJwtTokenCache;

    public String generateJwtToken(Authentication authentication) {

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    protected boolean isValidJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            validateTokenIsNotForALoggedOutDevice(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new BadRequestException("Invalid JWT signature: \\n" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new BadRequestException("Invalid JWT token: \\n" + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new UnauthorizedException("JWT token is expired: \\n" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new BadRequestException("JWT token is unsupported: \\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new BadRequestException("JWT claims string is empty: \\n" + e.getMessage());
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("User unauthorized: \\n" + e.getMessage());

        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            throw new ServerException("Exception: \\n" + e.getMessage());
        }
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = loggedOutJwtTokenCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null) {
            String userEmail = previouslyLoggedOutEvent.getUserEmail();
            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            String errorMessage = String.format("Session expired: [%s] at [%s]. Please login again", userEmail, logoutEventDate);
            throw new ExpiredJwtException(new DefaultHeader(), this.getAllClaimsFromToken(authToken), errorMessage);
        }
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //validate token
    public Boolean validateToken(String token) {
        final String username = getUserNameFromJwtToken(token);
        return username != null && !isTokenExpired(token);
    }

    public Date getTokenExpiryFromJWT(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }
}

