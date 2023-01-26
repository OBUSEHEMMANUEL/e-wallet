package com.example.ewallet.service;

import com.example.ewallet.ExceptionHandler.ErrorDetails;
import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.request.ChangePasswordRequest;
import com.example.ewallet.dtos.request.LoginRequest;
import com.example.ewallet.dtos.response.AddCardResponse;
import com.example.ewallet.dtos.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardService cardService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;


    @Override
    public String createAccount(User user) {
        userRepository.save(user);

        return generateToken(user);
    }

    @Override
    public String generateToken(User user) {
        String token = "";
        StringBuffer tok = new StringBuffer();
        SecureRandom number = new SecureRandom();
        for (int i = 0; i < 4; i++) {
            int num = number.nextInt(9);
            tok.append(num);
        }
        token = tok.toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        confirmationToken.setUser(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Override
    public AddCardResponse addCard(AddCardRequest addCardRequest) {
        var user=userRepository.findById(addCardRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        boolean isCardExist = cardService.findCard(addCardRequest.getCard().getCardNo()).isPresent();

        if (isCardExist) {
            throw new RuntimeException("Card already exist");
        } else {
            cardService.addCard(addCardRequest.getCard());
            user.getUserCards().add(addCardRequest.getCard());
            userRepository.save(user);
        }

        AddCardResponse response = new AddCardResponse();
        response.setMessage("card added successfully");
        response.setStatusCode(HttpStatus.OK);
        return response;
    }

    @Override
    public void enableUser(String emailAddress) {
     var user =   userRepository.findByEmailAddressIgnoreCase(emailAddress).orElseThrow(()-> new RuntimeException("email not found"));
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