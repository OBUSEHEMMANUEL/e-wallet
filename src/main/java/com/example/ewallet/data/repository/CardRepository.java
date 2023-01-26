package com.example.ewallet.data.repository;

import com.example.ewallet.data.models.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByCardNo(String cardNo);
    Card findByCardId(String cardId);
}
