package com.jca.thedoor.controllers;

import com.jca.thedoor.controllers.validators.RoleValidation;
import com.jca.thedoor.controllers.validators.UserValidation;
import com.jca.thedoor.entity.mongodb.Authentication;
import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.repository.mongodb.AuthenticationRepository;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.requests.UserRegisterRequest;
import com.jca.thedoor.util.MessageUtil;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository,
                          AuthenticationRepository authenticationRepository,
                          PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationRepository = authenticationRepository;
        this.encoder = encoder;
    }

    @PutMapping("/register")
    //@PreAuthorize("hasRole('ROLE_SUPER')")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterRequest userRequest) {
        RoleValidation roleValidation = new RoleValidation(roleRepository);
        try {
            roleValidation.validateListOfRoleExists(userRequest.getIdRoles());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        try {
            User user = getUserFromRequest(userRequest);
            User userCreated = userRepository.save(user);
            if (userCreated != null && userCreated.get_id() != null) {
                Authentication authentication = getAuthenticationFromRequestAndUser(userRequest,
                        userCreated.get_id().toHexString());
                authenticationRepository.save(authentication);
                return ResponseEntity.ok(userCreated);
            }
            throw new ServerException("Unexpected exception. user not created");// TODO displaykey
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getFieldFromDuplicateKeyExceptionMessage(e.getMessage()));
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @PutMapping("/registeradmin")
    public ResponseEntity<User> registerAdmin(@Valid @RequestBody UserRegisterRequest userRequest) {
        String userAdmin = "juancamiloarango@gmail.com";//TODO displaykey
        if (!userRequest.getUsername().equals(userAdmin)) {
            throw new NotFoundException("username is not admin"); // TODO displaykey
        }
        RoleValidation roleValidation = new RoleValidation(roleRepository);
        try {
            roleValidation.validateListOfRoleExists(userRequest.getIdRoles());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        try {
            User user = getUserFromRequest(userRequest);
            User userCreated = userRepository.save(user);
            if (userCreated != null && userCreated.get_id() != null) {
                Authentication authentication = getAuthenticationFromRequestAndUser(userRequest,
                        userCreated.get_id().toHexString());
                authenticationRepository.save(authentication);
                return ResponseEntity.ok(userCreated);
            }
            throw new ServerException("Unexpected exception. user not created");// TODO displaykey
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getFieldFromDuplicateKeyExceptionMessage(e.getMessage()));
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    private Authentication getAuthenticationFromRequestAndUser(UserRegisterRequest userRequest, String idUser) {
        return new Authentication(userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()), idUser,
                Arrays.stream(userRequest.getIdRoles()).toList());
    }

    private static User getUserFromRequest(UserRegisterRequest userRequest) {
        User user = new User(null, userRequest.getFullName(), userRequest.getEmail(),
                userRequest.getCellPhoneNumber(), userRequest.getCodCountry(),
                userRequest.getTokenExchange());
        return user;
    }

    // @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_SUPER') or hasRole('ROLE_TEST') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        UserValidation validation = new UserValidation(user, roleRepository, userRepository);
        validation.validateToUpdate();

        try {
            user.set_id(new ObjectId(user.getId()));
            User updated = userRepository.save(user);
            return ResponseEntity.ok(updated);
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
            //return (ResponseEntity<User>) ResponseEntity.badRequest();
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException(e.getMessage());
        }
    }

    //TODO unit test bd
    //@CrossOrigin(origins = "http://localhost:3000")
    //@PreAuthorize("hasRole('ROLE_SUPER') or hasRole('ROLE_TEST') or hasRole('ROLE_USER')")
    @PostMapping("/findByUserName")
    public ResponseEntity<User> findByUserName(@RequestBody User user) {
        UserValidation.validateMandatoryFieldsToSearch(user);
        User found = userRepository.findFirstByUserNameOrEmailOrCellPhoneNumber(
                null, user.getEmail(), user.getCellPhoneNumber());
        found.setId(found.get_id().toString()); //TODO consider move it to a service class
        if (found == null) {
            throw new NotFoundException("Usuario no encontrado, revise la información enviada");
        }
        // found.setId(found.get_id().toString());
        return ResponseEntity.ok(found);
    }

    //TODO unit test bd
    //@PreAuthorize("hasRole('ROLE_SUPER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody User user) {
        try {
            userRepository.delete(user);
            return ResponseEntity.ok("Registro eliminado corréctamente");
        } catch (IllegalStateException e) {
            throw new BadRequestException(e.getMessage());
        }

    }
}
