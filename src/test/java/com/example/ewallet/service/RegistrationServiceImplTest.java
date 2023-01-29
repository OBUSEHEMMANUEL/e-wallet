package com.example.ewallet.service;

import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationServiceImplTest {
    @Autowired
    private RegistrationService registrationService;
    @BeforeEach
    void setUp() {

    }

    @Test
    void register() throws MessagingException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmailAddress("me@gmail.com");
        registrationRequest.setFirstName("Habeeb");
        registrationRequest.setLastName("Hbabeb");
        registrationRequest.setPassword("greeddkdkdk");
        RegistrationResponse response = registrationService.register(registrationRequest);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }
}