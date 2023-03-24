package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.security.jwt.JwtTokenUtil;
import com.jca.thedoor.security.payload.JwtResponse;
import com.jca.thedoor.security.payload.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authManager,
                          JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findFirstByUserNameOrEmailOrCellPhoneNumber(loginRequest.getUsername(),
                loginRequest.getUsername(), loginRequest.getUsername());
        user.setId(user.get_id().toString()); //TODO consider move it to a service class
        return ResponseEntity.ok(new JwtResponse(jwt, user));
    }

    // TODO set userRoles in response of User
}
