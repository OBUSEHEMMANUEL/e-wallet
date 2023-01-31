package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
    Optional<ConfirmationToken> findByToken(String token);
    ConfirmationToken findByUserId(String id);
    void deleteConfirmationTokensByExpiredAtBefore(LocalDateTime current);
    void confirmAt(LocalDateTime now, String confirmationToken);
}