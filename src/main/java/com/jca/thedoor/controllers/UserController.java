package com.jca.thedoor.controllers;

import com.jca.thedoor.controllers.validators.UserValidation;
import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.MissingFieldException;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.security.payload.RegisterRequest;
import com.jca.thedoor.util.MessageUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest signUpRequest) {
        //First validate
        UserValidation validation = new UserValidation(userRepository, signUpRequest);
        validation.validateAll();

        // Create new user
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        User user = signUpRequest.getUserFromRequest();
        User created = userRepository.save(user);

        return ResponseEntity.ok(created);
    }
}
