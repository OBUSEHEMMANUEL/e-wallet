package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.request.DeleteCardRequest;
import com.example.ewallet.dtos.request.UpdateCardRequest;
import com.example.ewallet.dtos.response.AddCardResponse;
import com.example.ewallet.dtos.response.DeleteCardResponse;
import com.example.ewallet.dtos.response.UpdateCardResponse;

import java.util.Optional;

public interface CardService {
   void addCard(Card card);
   Optional<Card> findCard(String cardNo);
   Card findByCardId(String cardId);
   void deleteCard(String id);
}
