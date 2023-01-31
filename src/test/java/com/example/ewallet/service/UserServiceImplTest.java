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
import java.util.Optional;
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

    User user;
    private KycRequest kycRequest;
    private KycUpdateRequest kycUpdateRequest;
    private RegistrationRequest registrationRequest;
    private Card card;

    private NextOfKin nextOfKin;
    private InitiateTransferRequest initiateTransferRequest;

    @BeforeEach
    public void setThis(){
        user = new User();
        user.setPassword("12345");
        user.setEmailAddress("okorojeremiah930@gmail.com");
        user.setFirstName("Jeremiah");
        user.setFirstName("Okoro");

         nextOfKin = new NextOfKin();
        nextOfKin.setNextOfKinFullName("Abowale Olabisi");
        nextOfKin.setEmailAddress("Adebowale45@gamil.com");
        nextOfKin.setPhoneNumber("08136548945");
        nextOfKin.setRelationship("Brother");





        card = new Card();
        card.setCardNo("5399834400039582");
        card.setCardName("Master");
        card.setUserId("63d713c7a475f56a05e69137");
        card.setCvv("990");
        card.setExpireDate("09/24");
    }
    @Test
    void createAccountTest(){
        String token =   userService.createAccount(user);
//        assertEquals();
        assertNotNull(token);
    }
    @Test
    void generateToken() {
//        String  token =    userService.generateToken(user);
//        assertNull(token);
/*
Note: Token cannot be generated without user id.
 */
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
    void addCard() throws IOException, MessagingException {
        RegistrationRequest regRequest = new RegistrationRequest();
        regRequest.setEmailAddress("habb@gmail.com");
        regRequest.setFirstName("Habeeb");
        regRequest.setLastName("Ahmad");
        regRequest.setPassword("hab5real");
        registrationService.register(regRequest);
        Optional<User> user = userService.findUser(regRequest.getEmailAddress());
        System.out.println(user);
        Card card = new Card();
        card.setCardNo("4920690287056283");
        card.setCardName("Ahmad Ajibola");
        card.setCvv("882");
        card.setExpireDate("1/25");
        card.setUserId(user.get().getId());
        AddCardRequest addCard = new AddCardRequest();
        addCard.setCard(card);
        addCard.setUserId(user.get().getId());

        AddCardResponse response = userService.addCard(addCard);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void doKyc() throws MessagingException {


        RegistrationRequest regRequest = new RegistrationRequest();
        regRequest.setEmailAddress("jkr@gmail.com");
        regRequest.setFirstName("kb");
        regRequest.setLastName("Ahmad");
        regRequest.setPassword("hab5real");
        registrationService.register(regRequest);
        var user = userService.findUser("jkr@gmail.com").get();

        kycRequest = new KycRequest();
        kycRequest.setBvn("01786345678");
        kycRequest.setCardType(CardType.VOTERS_CARD);
        kycRequest.setHomeAddress("No 34, Ademiwa Street, Yaba, Lagos");
        kycRequest.setNextOfKin(nextOfKin);
        kycRequest.setUserId(user.getId());

        KycResponse response = userService.doKyc(kycRequest);

        User user1 = userService.findUserById(user.getId()).get();
        boolean kyc = user1.isCompletedKyc();
        assertTrue(kyc);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateKyc() {
        Optional<User> user = userService.findUser("habeeb@gmail.com");

        kycUpdateRequest = new KycUpdateRequest();
        kycUpdateRequest.setKycId("63d7142be63cde4ac068e2de");
        kycUpdateRequest.setUserId(user.get().getId());
        kycUpdateRequest.setCardType(CardType.DRIVERS_LICENCE);
        kycUpdateRequest.setNextOfKin(nextOfKin);
        kycUpdateRequest.setHomeAddress("No 419, mobolowowan Street, Yaba, Lagos");
        kycUpdateRequest.setPassword("12345");


        userService.updateKyc(kycUpdateRequest);
        Kyc kyc = kycService.findKycByUserId(kycRequest.getUserId());
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
    void initiateTransfer() throws IOException {
        initiateTransferRequest = new InitiateTransferRequest();
        initiateTransferRequest.setAmount("37800");
        initiateTransferRequest.setReason("Holiday Flexing");
        initiateTransferRequest.setReference("f1c0ee68-d6be-480f-82bc-da6a8ff1cb3b");
        initiateTransferRequest.setSource("balance");
        initiateTransferRequest.setRecipient("RCP_mqufexy3vrjozve");

        var user =   userService.initiateTransfer(initiateTransferRequest);
        assertEquals("You cannot initiate third party payouts as a starter business",user.getMessage());

    }



}