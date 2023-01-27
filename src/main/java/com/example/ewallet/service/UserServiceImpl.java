package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.*;
import com.example.ewallet.dtos.response.*;
import com.example.ewallet.utils.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CardService cardService;
    private KycService kycService;
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CardService cardService, KycService kycService, ConfirmationTokenService confirmationTokenService){
        this.kycService = kycService;
        this.userRepository = userRepository;
        this.cardService = cardService;
        this.confirmationTokenService = confirmationTokenService;
    }


    @Override
    public String createAccount(User user) {
        userRepository.save(user);

        return generateToken(user);
    }

    @Override
    public String generateToken(User user) {
        StringBuilder tok = new StringBuilder();
        SecureRandom number = new SecureRandom();
        for (int i = 0; i < 4; i++) {
            int num = number.nextInt(9);
            tok.append(num);
        }
        StringBuilder token = new StringBuilder(tok.toString());
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(String.valueOf(token));
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        confirmationToken.setUser(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token.toString();
    }

    @Override
    public AddCardResponse addCard(AddCardRequest addCardRequest) {
        var optionalUser=userRepository.findById(addCardRequest.getUserId());
        boolean cardExist = cardService.findCard(addCardRequest
                .getCard().getCardNo()).isPresent();

        if (cardExist) {
            throw new RuntimeException("Card already exist");
        } else {
            CreditCardValidator.isValidCreditCard(addCardRequest.getCard());
            cardService.addCard(addCardRequest.getCard());
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                user.getUserCards().add(addCardRequest.getCard());
                userRepository.save(user);
            }

        }

        AddCardResponse response = new AddCardResponse();
        response.setMessage("card added successfully");
        response.setStatusCode(HttpStatus.OK);
        return response;
    }

    @Override
    public Optional<User> findUser(String userEmail) {
        return userRepository.findByEmailAddressIgnoreCase(userEmail);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public KycResponse doKyc(KycRequest kycRequest) {
        User user = userRepository.findById(kycRequest.getUserId())
                .orElseThrow(()->new RuntimeException("You are not a registered user"));
        boolean idExist = userRepository.findById(kycRequest.getUserId()).isPresent();
        setKycDetails(kycRequest, idExist, user);
        KycResponse kycResponse = new KycResponse();
        kycResponse.setMessage("Thank you for completing the kyc process");
        kycResponse.setStatusCode(HttpStatus.OK);
        return kycResponse;
    }

    private void setKycDetails(KycRequest kycRequest, boolean idExist, User user) {
        if (idExist && !user.isCompletedKyc()) {
            Kyc kyc = Kyc.builder()
                    .nextOfKin(kycRequest.getNextOfKin())
                    .cardType(kycRequest.getCardType())
                    .homeAddress(kycRequest.getHomeAddress())
                    .build();
            user.setCompletedKyc(true);
            kyc.setUserId(user.getId());
            kycService.saveKyc(kyc);
            userRepository.save(user);
        } else {
            throw new RuntimeException("You can no longer complete the kyc process, " +
                    "kindly use the update feature to update your details");
        }
    }

    @Override
    public KycUpdateResponse updateKyc(KycUpdateRequest kycUpdateRequest) {
        User user = userRepository.findById(kycUpdateRequest.getUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist"));
        Kyc kyc = kycService.findKyc(kycUpdateRequest.getKycId())
                .orElseThrow(()-> new RuntimeException("No kyc details found"));
        if (user.getPassword().equals(kycUpdateRequest.getPassword())) {
            kyc.setHomeAddress(kycUpdateRequest.getHomeAddress());
            kyc.setCardType(kycUpdateRequest.getCardType());
            kyc.setNextOfKin(kycUpdateRequest.getNextOfKin());
            kycService.saveKyc(kyc);
        }
        KycUpdateResponse kycUpdateResponse = new KycUpdateResponse();
        kycUpdateResponse.setMessage("Updated successfully");
        kycUpdateResponse.setStatusCode(HttpStatus.OK);
        return kycUpdateResponse;
    }

    @Override
    public UpdateCardResponse updateCard(UpdateCardRequest updateCardRequest) {
        var user=userRepository.findById(updateCardRequest.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
       var foundCard = cardService.findByCardId(updateCardRequest.getCard().getCardId());
        boolean isCardExist = cardService.findCard(updateCardRequest.getCard()
                .getCardNo()).isPresent();

        if (!isCardExist) {
            throw new RuntimeException("Card does not exist");
        } else {
            foundCard.setCardNo(updateCardRequest.getCard().getCardNo());
            foundCard.setCardName(updateCardRequest.getCard().getCardName());
            foundCard.setCvv(updateCardRequest.getCard().getCvv());
            foundCard.setExpireDate(updateCardRequest.getCard().getExpireDate());
            cardService.addCard(foundCard);
            userRepository.save(user);
        }

        UpdateCardResponse response = new UpdateCardResponse();
        response.setMessage("card updated successfully");
        response.setStatusCode(HttpStatus.OK);
        return response;

    }

    @Override
    public Set<Card> findUserCards(String userId) {
         var user = userRepository.findById(userId)
                 .orElseThrow(()-> new IllegalStateException("User Not Found"));
        return user.getUserCards();
    }


    @Override
    public DeleteCardResponse deleteCard(DeleteCardRequest deleteCardRequest) {
        var user= userRepository.findById(deleteCardRequest.getUserId()).
                orElseThrow(() -> new RuntimeException("user not found"));
        var card = cardService.findByCardId(deleteCardRequest.getCardId());
                if(card == null) throw new IllegalStateException("Card Not Found");
        user.getUserCards().remove(card);
        userRepository.save(user);
        cardService.deleteCard(deleteCardRequest.getCardId());

        DeleteCardResponse deleteCardResponse = new DeleteCardResponse();
        deleteCardResponse.setStatusCode(HttpStatus.OK);
        deleteCardResponse.setMessage("Card deleted successfully");
        return deleteCardResponse;
    }

    @Override
    public void enableUser(String emailAddress) {
     var user =   userRepository.findByEmailAddressIgnoreCase(emailAddress)
             .orElseThrow(()-> new RuntimeException("email not found"));
     user.setDisabled(false);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var foundUser = userRepository.findByEmailAddressIgnoreCase(loginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email not found"));
        LoginResponse loginResponse = new LoginResponse();
        if (foundUser.getPassword().equals(loginRequest.getPassword())) {
            loginResponse.setMessage("login successful");
            loginResponse.setStatusCode(HttpStatus.OK);
        }
        else {
            loginResponse.setMessage("re-login");
            loginResponse.setStatusCode(HttpStatus.BAD_REQUEST);
            throw new IllegalStateException("incorrect password");
        }
        return loginResponse;
    }
    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        var foundUser = userRepository.
                findByEmailAddressIgnoreCase(changePasswordRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email not found"));

        if(foundUser.getPassword().equals(changePasswordRequest.getOldPassword())){
      foundUser.setPassword(changePasswordRequest.getNewPassword());
      userRepository.save(foundUser);

        }

        return "Password Changed Successfully";

    }




}