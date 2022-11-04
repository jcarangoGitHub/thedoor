package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.security.jwt.JwtTokenUtil;
import com.jca.thedoor.security.payload.JwtResponse;
import com.jca.thedoor.security.payload.LoginRequest;
import com.jca.thedoor.security.payload.RegisterRequest;
import com.jca.thedoor.util.MessageUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest signUpRequest) {

        // Check 1: username
        if (userRepository.findFirstByUserNameExists(signUpRequest.getUserName())) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Nombre de usuario"}));
        }

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Email"}));
        }

        // Check 3: cellPhoneNumber
        if (userRepository.existsByCellPhoneNumber(signUpRequest.getCellPhoneNumber())) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Número celular"}));
        }

        // Create new user's account
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        User user = signUpRequest.getUserFromRequest();
        User created = userRepository.save(user);

        return ResponseEntity.ok(created);
    }
}
