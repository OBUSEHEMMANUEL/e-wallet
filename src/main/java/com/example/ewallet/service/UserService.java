package com.example.ewallet.service;

import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.ChangePasswordRequest;
import com.example.ewallet.dtos.request.LoginRequest;
import com.example.ewallet.dtos.response.LoginResponse;

public interface UserService {
    String createAccount(User user);

    void enableUser(String emailAddress);
    LoginResponse login(LoginRequest loginRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);

   String  generateToken(User user);
}
