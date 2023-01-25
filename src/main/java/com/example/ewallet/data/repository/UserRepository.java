package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
//    @Transactional
//    @Query("UPDATE User user " +
//            "SET user.isDisabled = false" +
//            " WHERE user.emailAddress=?1")
//    void enable(String emailAddress);
}
