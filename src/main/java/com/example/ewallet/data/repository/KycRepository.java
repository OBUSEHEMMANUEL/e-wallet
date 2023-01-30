package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface KycRepository extends MongoRepository<Kyc, String> {
    Optional<Kyc> findById(String id);
    Kyc findByUserId(String userId);
}
