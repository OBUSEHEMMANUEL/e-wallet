package com.example.ewallet.service;

import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.ChangePasswordRequest;
import com.example.ewallet.dtos.request.LoginRequest;

public interface UserService {
    String createAccount(User user);

    void enableUser(String emailAddress);
    String login(LoginRequest loginRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);

   String  generateToken(User user);
}
