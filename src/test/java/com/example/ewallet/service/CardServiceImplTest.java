package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.repository.CardRepository;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.response.CardValidationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardServiceImplTest {
    @Autowired
    private CardService cardService;

    @Test
    void addCard() {
        Card card = new Card();
        card.setCardNo("1234567899991444");
        card.setCardName("Gamos Ramos");
        card.setCvv("789");
        card.setExpireDate("4/23");
        cardService.addCard(card);
        Optional<Card> foundCard = cardService.findCard("1234567899991444");
        assertEquals("Gamos Ramos", foundCard.get().getCardName());
    }


    @Test
    void findByCardId() {
        Card foundCard = cardService.findByCardId("63d7149d8bc78600b35ad5f1");
        assertEquals("Gamos Ramos", foundCard.getCardName());
    }

    @Test
    void deleteCard() {
        cardService.deleteCard("63d7149d8bc78600b35ad5f1");
        Optional<Card> foundCard = cardService.findCard("1234567899991444");
        assertEquals(Optional.empty(), foundCard);
    }

//    @Test
//    void validateCreditCard() throws IOException {
//        Card card = new Card();
//        card.setCardNo("4920690019097290");
//        card.setCardName("Ahmad CCCC");
//        card.setCvv("702");
//        card.setExpireDate("01/25");
//        AddCardRequest addCardRequest = new AddCardRequest();
//        addCardRequest.setCard(card);
//        addCardRequest.setUserId("63d6fdab5d9a237a03b839b7");
//        CardValidationResponse response = cardService.validateCreditCard(addCardRequest);
//    }
}