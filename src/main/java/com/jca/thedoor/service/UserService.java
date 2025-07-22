package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.User;

public interface UserService {
    User findFirstByUserNameOrEmailOrCellPhoneNumber(String userName, String email, String cellPhoneNumber);
}
