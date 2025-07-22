package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMongoService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findFirstByUserNameOrEmailOrCellPhoneNumber(String userName, String email, String cellPhoneNumber) {
        User user = userRepository.findFirstByUserNameOrEmailOrCellPhoneNumber(userName, email, cellPhoneNumber);
        if (user != null) {
            user.setId(user.get_id().toString());
            return user;
        }

        throw new NotFoundException("Usuario no encontrado, revise la información enviada");
    }
}
