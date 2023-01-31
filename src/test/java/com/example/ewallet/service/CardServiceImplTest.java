package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.CardRepository;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.response.CardValidationResponse;
import com.example.ewallet.utils.InvalidCreditCardNumberException;
import jakarta.mail.MessagingException;
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
    @Autowired
    private UserService userService;
    @Autowired
    private RegistrationService registrationService;
    @Test
    void addCard() throws MessagingException, IOException {
        Card card = new Card();
        card.setCardNo("4920690019097291");
        card.setCardName("Ahmad Ajibola");
        card.setCvv("802");
        cardService.addCard(card);
        Optional<Card> foundCard = cardService.findCard("4920690019097291");
        assertEquals("Ahmad Ajibola", foundCard.get().getCardName());
    }

    @Test
    void testThatInvalidCardExceptionIsThrownWhenAddingInvalidCard() throws MessagingException {
        Card card = new Card();
        card.setCardNo("1234123412341234");
        card.setCardName("Free card");
        card.setCvv("105");
        card.setExpireDate("1/25");
        AddCardRequest addCardRequest = new AddCardRequest();
        addCardRequest.setCard(card);
        assertThrows(InvalidCreditCardNumberException.class,()->cardService.validateCreditCard(addCardRequest));
    }
    @Test
    void findByCardId() throws IOException {
        Card card = new Card();
        card.setCardNo("4920690119097293");
        card.setCardName("Ahmad Ajibola");
        card.setCvv("802");
        card.setExpireDate("1/25");
        cardService.addCard(card);
        Optional<Card> foundCard = cardService.findCard(card.getCardNo());
        Card found = cardService.findByCardId(foundCard.get().getCardId());
        assertEquals("Ahmad Ajibola", found.getCardName());
    }

    @Test
    void deleteCard() throws IOException {
        Card card = new Card();
        card.setCardNo("4920690117095293");
        card.setCardName("Ahmad Ajibola");
        card.setCvv("802");
        card.setExpireDate("1/25");
        cardService.addCard(card);
        Optional<Card> foundCard = cardService.findCard(card.getCardNo());
        cardService.deleteCard(foundCard.get().getCardId());
        Optional<Card> deletedCard = cardService.findCard(card.getCardNo());
        assertEquals(Optional.empty(), deletedCard);
    }

}