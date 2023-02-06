package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.*;
import com.example.ewallet.dtos.response.*;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private KycService kycService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final String SECRET_KEY = System.getenv("YOUR_SECRET_KEY");



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
    public VerificationResponse verifyRecieversAccount(AccountVerificationRequest verificationRequest) throws IOException {

         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                 .url("https://api.paystack.co/bank/resolve?account_number=" + verificationRequest.getAccountNo() +
                         "&bank_code=" + verificationRequest.getBankCode())
                 .get()
                 .addHeader("Authorization","Bearer "+SECRET_KEY)
                 .build();
         try (ResponseBody response = client.newCall(request).execute().body()) {
             Gson gson = new Gson();
             VerificationResponse verificationResponse = gson.fromJson(response.string(), VerificationResponse.class);
             if (verificationResponse.getData() == null) throw new RuntimeException("Invalid account number");
             return verificationResponse;
         }
    }



    @Override
    public AddCardResponse addCard(AddCardRequest addCardRequest) throws IOException {
        var optionalUser=userRepository.findById(addCardRequest.getUserId());
        boolean cardExist = cardService.findCard(addCardRequest
                .getCard().getCardNo()).isPresent();

        if (cardExist) {
            throw new RuntimeException("Card already exist");
        } else {
            cardService.validateCreditCard(addCardRequest);
            cardService.addCard(addCardRequest);
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
    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
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
                    .bvn(kycRequest.getBvn())
                    .nextOfKin(kycRequest.getNextOfKin())
                    .cardType(kycRequest.getCardType())
                    .homeAddress(kycRequest.getHomeAddress())
                    .userId(user.getId())
                    .build();

//            kyc.setUserId(user.getId());
            kycService.saveKyc(kyc);
            user.setCompletedKyc(true);
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
    public UpdateCardResponse updateCard(UpdateCardRequest updateCardRequest) throws IOException {
        var user=userRepository.findById(updateCardRequest.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
       var foundCard = cardService.findByCardId(updateCardRequest.getCardId());

        if (foundCard == null) {
            throw new RuntimeException("Card does not exist");
        } else {
            if (!Objects.equals(cardService.validateCardNumber(updateCardRequest.getCardNo()), ""))
                foundCard.setCardNo(updateCardRequest.getCardNo());
            else foundCard.setCardNo(foundCard.getCardNo());
            if (!updateCardRequest.getCardName().isEmpty()) foundCard.setCardName(updateCardRequest.getCardName());
            else foundCard.setCardName(foundCard.getCardName());
            if (!updateCardRequest.getCardCvv().isEmpty()) foundCard.setCvv(updateCardRequest.getCardCvv());
            else foundCard.setCvv(foundCard.getCvv());
            if (updateCardRequest.getExpiryDate().isEmpty()) foundCard.setExpireDate(updateCardRequest.getExpiryDate());
            else foundCard.setExpireDate(foundCard.getExpireDate());
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
    public CreateRecipientResponse createRecipient(CreateTransferRecipient createTransferRecipient) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", createTransferRecipient.getType());
            jsonObject.put("name", createTransferRecipient.getName());
            jsonObject.put("account_number", createTransferRecipient.getAccount_number());
            jsonObject.put("bank_code", createTransferRecipient.getBank_code());
            jsonObject.put("currency", createTransferRecipient.getCurrency());
        }catch (JSONException ex){
            System.out.print(ex.getMessage());
        }
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://api.paystack.co/transferrecipient")
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();


        try (ResponseBody response = okHttpClient.newCall(request).execute().body()){
            Gson gson = new Gson();
            CreateRecipientResponse createRecipientResponse = gson.fromJson(response.string(), CreateRecipientResponse.class);
            if (createRecipientResponse.getData() == null) throw new RuntimeException("Invalid account");
            return  createRecipientResponse;
        }
    }

    @Override
    public InitiateTransferResponse initiateTransfer(InitiateTransferRequest transferRequest) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("source", transferRequest.getSource());
            jsonObject.put("amount", transferRequest.getAmount());
            jsonObject.put("reference", transferRequest.getReference());
            jsonObject.put("recipient", transferRequest.getRecipient());
            jsonObject.put("reason", transferRequest.getReason());
        }catch (JSONException ex){
            log.info(ex.getMessage());
        }
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://api.paystack.co/transfer")
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try (ResponseBody response = okHttpClient.newCall(request).execute().body()){
            Gson gson = new Gson();
            InitiateTransferResponse initiateTransferResponse = gson.fromJson(response.string(), InitiateTransferResponse.class);

            return  initiateTransferResponse;
        }
    }


    @Override
    public void enableRegisteredUser(String emailAddress) {
        Optional<User> foundUser = userRepository.findByEmailAddressIgnoreCase(emailAddress);
        foundUser.get().setEnabled(true);
        userRepository.save(foundUser.get());
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