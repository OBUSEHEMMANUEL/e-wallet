package com.example.ewallet.service;

import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.ChangePasswordRequest;
import com.example.ewallet.dtos.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public String createAccount(User user) {
        userRepository.save(user);

        return generateToken(user);
    }

    private String generateToken(User user) {
        String token = "";
        StringBuffer tok = new StringBuffer();
        SecureRandom number = new SecureRandom();
        for (int i =0 ; i < 4; i++){
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
    public void enableUser(String emailAddress) {

    }

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }
}
