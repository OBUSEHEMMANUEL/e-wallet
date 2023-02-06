package com.example.ewallet.service;

import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.ConfirmTokenRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.request.ResendTokenRequest;
import com.example.ewallet.dtos.request.SetPasswordRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationServiceImplTest {
    @Autowired
    private RegistrationService registrationService;
    private ConfirmTokenRequest confirmTokenRequest;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private UserService userService;
    @BeforeEach
    void setUp() {

    }

    @Test
    void register() throws MessagingException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmailAddress("mme@gmail.com");
        registrationRequest.setFirstName("Habeeb");
        registrationRequest.setLastName("Hbabeb");
        registrationRequest.setPassword("greeddkdkdk");
        RegistrationResponse response = registrationService.register(registrationRequest);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }
    @Test
    void testConfirmationWithWrongTokenThrowsException(){
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
        confirmTokenRequest.setToken("1234");
        confirmTokenRequest.setEmailAddress("me@gmail.com");
        assertThrows(IllegalStateException.class, () -> registrationService.confirmToken(confirmTokenRequest));
    }

    @Test
    void testConfirmationToken() throws MessagingException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmailAddress("me33@gmail.com");
        registrationRequest.setFirstName("Habeeb");
        registrationRequest.setLastName("Hbabeb");
        registrationRequest.setPassword("greeddkdkdk");
        registrationService.register(registrationRequest);
        Optional<User> foundUser = userService.findUser("me33@gmail.com");
        String id= foundUser.get().getId();
        ConfirmationToken token= confirmationTokenService.findTokenByUserId(id);
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
        confirmTokenRequest.setToken(token.getToken());
        confirmTokenRequest.setEmailAddress("me33@gmail.com");
        assertEquals("confirmed", registrationService.confirmToken(confirmTokenRequest).getMessage());
    }
    @Test
    void testResendToken() throws MessagingException {
        ResendTokenRequest resendTokenRequest = new ResendTokenRequest();
        resendTokenRequest.setEmailAddress("mme@gmail.com");
        resendTokenRequest.setPassword("greeddkdkdk");
        String token = registrationService.resendToken(resendTokenRequest);
        assertNotNull(token);
    }
    @Test
    void testSetPassword(){
        SetPasswordRequest setPasswordRequest = new SetPasswordRequest();
        setPasswordRequest.setEmailAddress("mme@gmail.com");
        setPasswordRequest.setNewPassword("ahmad");
        registrationService.setPassword(setPasswordRequest);
        Optional<User> foundUser = userService.findUser(setPasswordRequest.getEmailAddress());
        assertEquals("ahmad", foundUser.get().getPassword());
    }
}