package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{
    @Autowired
    private CardRepository cardRepository;
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
