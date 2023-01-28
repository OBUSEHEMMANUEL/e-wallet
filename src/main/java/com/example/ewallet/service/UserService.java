package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.*;
import com.example.ewallet.dtos.response.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    String createAccount(User user);

    void enableUser(String emailAddress);
    LoginResponse login(LoginRequest loginRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);

   String  generateToken(User user);

   VerificationResponse verifyRecieversAccount(AccountVerificationRequest verificationRequest) throws IOException;
   AddCardResponse addCard(AddCardRequest addCardRequest) throws IOException;

   Optional<User> findUser(String userEmail);

    void saveUser(User user);
   KycResponse doKyc(KycRequest kycRequest);

   KycUpdateResponse updateKyc(KycUpdateRequest kycUpdateRequest);


    UpdateCardResponse updateCard(UpdateCardRequest updateCardRequest);

    Set<Card> findUserCards(String id);
    DeleteCardResponse deleteCard(DeleteCardRequest deleteCardRequest);

    CreateRecipientResponse createRecipient(CreateTransferRecipient createTransferRecipient) throws IOException;


}

