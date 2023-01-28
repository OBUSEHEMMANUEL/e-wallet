package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.response.*;

import java.io.IOException;
import java.util.Optional;

public interface CardService {
   void addCard(Card card);
   Optional<Card> findCard(String cardNo);
   Card findByCardId(String cardId);
   void deleteCard(String id);
   CardValidationResponse validateCreditCard(AddCardRequest addCardRequest) throws IOException;
}
