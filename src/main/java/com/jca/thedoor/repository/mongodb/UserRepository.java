package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{userName: '?0'}", exists = true)
    Boolean findFirstByUserNameExists(String userName);

    @Query(value = "{email: '?0'}", exists = true)
    Boolean existsByEmail(String email);

    @Query(value = "{cellPhoneNumber: '?0'}", exists = true)
    Boolean existsByCellPhoneNumber(String cellPhoneNumber);

    @Query("{'$or': [{userName: '?0'}, {email: '?1'}, {cellPhoneNumber: '?2'}]}")
    User findFirstByUserNameOrEmailOrCellPhoneNumber(String userName, String email, String cellPhoneNumber);

    @Query()
    User findFirstByUserNameOrEmailOrCellPhoneNumberAndPassword(String userName, String password);
}
