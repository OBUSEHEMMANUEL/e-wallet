package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
    //void enable(String emailAddress);
}
