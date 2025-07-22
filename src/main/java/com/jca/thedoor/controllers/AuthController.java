package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.security.jwt.JwtTokenUtil;
import com.jca.thedoor.security.payload.JwtResponse;
import com.jca.thedoor.security.payload.LoginRequest;
import com.jca.thedoor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * Controlador para llevar a cabo la autenticación utilizando JWT
 *
 * Se utiliza AuthenticationManager para autenticar las credenciales que son el
 * usuario y password que llegan por POST en el cuerpo de la petición
 *
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user login and authentication")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager,
                          JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    //@CrossOrigin(origins = "http://localhost:3000")// TODO hacer configuraciones para que este link dependa de variables según el ambiente
    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user using username, email or phone number and returns a JWT token if valid."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully authenticated",
            content = @Content(schema = @Schema(implementation = JwtResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<JwtResponse> login(@RequestBody(
            description = "User credentials for login",
            required = true,
            content = @Content(schema = @Schema(implementation = LoginRequest.class)))
                                                 @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest
            ) {

        try {
            User user = userService.findFirstByUserNameOrEmailOrCellPhoneNumber(
                    loginRequest.getUsername(),
                    loginRequest.getUsername(),
                    loginRequest.getUsername());
            if (user != null) {
                Authentication authentication = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtTokenUtil.generateJwtToken(authentication);

                return ResponseEntity.ok(new JwtResponse(jwt, user));
            }

            throw new ServerException("Unexpected!");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (NotFoundException e) {
            throw new BadCredentialsException("Credentials invalid");
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }

    }

    // TODO Hacer una pantalla para registrar usuarios
}
