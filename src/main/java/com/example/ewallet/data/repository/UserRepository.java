package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
//    @Transactional
//    @Query("UPDATE User user " +
//            "SET user.isDisabled = false" +
//            " WHERE user.emailAddress=?1")
//    void enable(String emailAddress);
}
