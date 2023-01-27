package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.*;
import com.example.ewallet.dtos.response.*;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    String createAccount(User user);

    void enableUser(String emailAddress);
    LoginResponse login(LoginRequest loginRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);

   String  generateToken(User user);
   AddCardResponse addCard(AddCardRequest addCardRequest);

   Optional<User> findUser(String userId);

   void saveUser(User user);

   KycResponse doKyc(KycRequest kycRequest);

   KycUpdateResponse updateKyc(KycUpdateRequest kycUpdateRequest);


    UpdateCardResponse updateCard(UpdateCardRequest updateCardRequest);

    Set<Card> findUserCards(String id);
    DeleteCardResponse deleteCard(DeleteCardRequest deleteCardRequest);
}

