package com.example.ewallet.service;

import com.example.ewallet.data.models.*;
import com.example.ewallet.dtos.request.*;
import com.example.ewallet.dtos.response.*;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private KycService kycService;
    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;
    @Autowired
    private RegistrationService registrationService;
    private KycRequest kycRequest;
    private KycUpdateRequest kycUpdateRequest;
    private RegistrationRequest registrationRequest;
    private Card card;

    @BeforeEach
    public void setThis(){
        registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("12345");
        registrationRequest.setEmailAddress("okorojeremiah930@gmail.com");
        registrationRequest.setFirstName("Jeremiah");
        registrationRequest.setFirstName("Okoro");

        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setNextOfKinFullName("Abowale Olabisi");
        nextOfKin.setEmailAddress("Adebowale45@gamil.com");
        nextOfKin.setPhoneNumber("08136548945");
        nextOfKin.setRelationship("Brother");

        kycRequest = new KycRequest();
        kycRequest.setBvn("01786345678");
        kycRequest.setCardType(CardType.VOTERS_CARD);
        kycRequest.setHomeAddress("No 34, Ademiwa Street, Yaba, Lagos");
        kycRequest.setNextOfKin(nextOfKin);
        kycRequest.setUserId("63d713c7a475f56a05e69137");

        kycUpdateRequest = new KycUpdateRequest();
        kycUpdateRequest.setKycId("63d7142be63cde4ac068e2de");
        kycUpdateRequest.setUserId("63d713c7a475f56a05e69137");
        kycUpdateRequest.setCardType(CardType.DRIVERS_LICENCE);
        kycUpdateRequest.setNextOfKin(nextOfKin);
        kycUpdateRequest.setHomeAddress("No 419, mobolowowan Street, Yaba, Lagos");
        kycUpdateRequest.setPassword("12345");

        card = new Card();
        card.setCardNo("5399834400039582");
        card.setCardName("Master");
        card.setUserId("63d713c7a475f56a05e69137");
        card.setCvv("990");
        card.setExpireDate("09/24");
    }
    @Test
    void registerTest() throws MessagingException {
        RegistrationResponse response = registrationService.register(registrationRequest);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Test
    void login() {
        LoginRequest login = new LoginRequest();
        login.setEmailAddress("okorojeremiah930@gmail.com");
        login.setPassword("12345");

        LoginResponse response = userService.login(login);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changePassword() {
        ChangePasswordRequest password = new ChangePasswordRequest();
        password.setNewPassword("54321");
        password.setOldPassword("12345");
        password.setEmailAddress("okorojeremiah930@gmail.com");
        userService.changePassword(password);

        User user = userService.findUser("okorojeremiah930@gmail.com").get();

        assertEquals("54321",user.getPassword());
    }

    @Test
    void verifyRecieversAccount() throws IOException {
        AccountVerificationRequest verification = new AccountVerificationRequest();
        verification.setAccountNo("0158333941");
        verification.setBankCode("058");

        VerificationResponse verify = userService.verifyRecieversAccount(verification);

        assertEquals("true", verify.getStatus());

    }

    @Test
    void addCard() throws IOException {
        AddCardRequest addCard = new AddCardRequest();
        addCard.setCard(card);
        addCard.setUserId("63d713c7a475f56a05e69137");

        AddCardResponse response = userService.addCard(addCard);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void doKyc() {
        assertThrows(RuntimeException.class, ()-> userService.doKyc(kycRequest));
        User user = userService.findUserById("63d713c7a475f56a05e69137").get();
        boolean kyc = user.isCompletedKyc();
        assertTrue(kyc);
    }

    @Test
    void updateKyc() {
        userService.updateKyc(kycUpdateRequest);
        Kyc kyc = kycService.findKycByUserId("63d713c7a475f56a05e69137");
        assertEquals(CardType.DRIVERS_LICENCE, kyc.getCardType());
        assertEquals("No 419, mobolowowan Street, Yaba, Lagos", kyc.getHomeAddress());
    }

    @Test
    void updateCard() {

    }

    @Test
    void findUserCards() {
        Set<Card> cards = userService.findUserCards("63d713c7a475f56a05e69137");
        assertNotNull(cards);
        System.out.println(cards);
    }

    @Test
    void deleteCard() {
        DeleteCardRequest deleteCard = new DeleteCardRequest();
        deleteCard.setCardId("63d73d0131e2a42c0346dd79");
        deleteCard.setUserId("63d713c7a475f56a05e69137");

        assertThrows(RuntimeException.class, ()-> userService.deleteCard(deleteCard));

        Card card = cardService.findByCardId("63d73d0131e2a42c0346dd79");
        assertNull(card);

    }

    @Test
    void createRecipient() throws IOException {
        CreateTransferRecipient createTransferRecipient = new CreateTransferRecipient();
        createTransferRecipient.setType("nuban");
        createTransferRecipient.setName("OKORO JEREMIAH");
        createTransferRecipient.setAccount_number("0158333941");
        createTransferRecipient.setCurrency("NGN");
        createTransferRecipient.setBank_code("058");

        CreateRecipientResponse response = userService.createRecipient(createTransferRecipient);
        assertNotNull(response);
        assertEquals("true", response.getStatus());
    }

    @Test
    void initiateTransfer() {
    }


}