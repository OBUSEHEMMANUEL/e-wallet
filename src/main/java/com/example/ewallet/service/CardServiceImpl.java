package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.repository.CardRepository;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.request.DeleteCardRequest;
import com.example.ewallet.dtos.request.UpdateCardRequest;
import com.example.ewallet.dtos.response.AddCardResponse;
import com.example.ewallet.dtos.response.DeleteCardResponse;
import com.example.ewallet.dtos.response.UpdateCardResponse;
import com.example.ewallet.utils.CreditCardValidator;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{

    private CardRepository cardRepository;


    public CardServiceImpl(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }
    @Override
    public void addCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Optional<Card> findCard(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }

    @Override
    public Card findByCardId(String cardId) {
        return cardRepository.findByCardId(cardId);
    }
    @Override
    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }
}
