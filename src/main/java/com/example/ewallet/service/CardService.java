package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;

import java.util.Optional;

public interface CardService {
   void addCard(Card card);
   Optional<Card> findCard(String cardNo);
   Card findByCardId(String cardId);

   void deleteCard(String id);
}
