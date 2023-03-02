package com.jca.thedoor.security.jwt;

import com.jca.thedoor.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Rechaza peticiones no autenticadas devolviendo
 * un código de error 401 unauthorized
 * devuelve 400 en caso de otro error
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        log.error(e.getMessage());
        if (e instanceof BadCredentialsException) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Bad credentials");
            return;
        } else if (e instanceof InsufficientAuthenticationException) {
            String msg = "You need to authenticate again. Perhaps your session expired";
            httpServletResponse.setHeader("msg", msg);
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, msg);
            return;
        } else if (e.getCause() instanceof UnauthorizedException) {
            httpServletResponse.setHeader("msg", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                e.getMessage());
    }
}
