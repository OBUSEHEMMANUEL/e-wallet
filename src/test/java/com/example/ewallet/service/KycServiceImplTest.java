package com.example.ewallet.service;

import com.example.ewallet.data.models.CardType;
import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.NextOfKin;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.KycRepository;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class KycServiceImplTest {
    @Autowired
    private KycService kycService;
    @Autowired
    private UserService userService;
    private  Kyc kyc;
    private KycUpdateRequest kycUpdateRequest;

    @BeforeEach
    public void setThis(){
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("12345");
        registrationRequest.setEmailAddress("okorojeremiah920@gmail.com");
        registrationRequest.setFirstName("Jeremiah");
        registrationRequest.setFirstName("Okoro");

        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setNextOfKinFullName("Abowale Olabisi");
        nextOfKin.setEmailAddress("Adebowale45@gamil.com");
        nextOfKin.setPhoneNumber("08136548945");
        nextOfKin.setRelationship("Brother");

        kyc = new Kyc();
        kyc.setBvn("01786345678");
        kyc.setCardType(CardType.VOTERS_CARD);
        kyc.setHomeAddress("No 34, Ademiwa Street, Yaba, Lagos");
        kyc.setNextOfKin(nextOfKin);
        kyc.setUserId("63d713c7a475f56a05e69137");

        kycUpdateRequest = new KycUpdateRequest();
        kycUpdateRequest.setKycId("63d7142be63cde4ac068e2de");
        kycUpdateRequest.setUserId("63d713c7a475f56a05e69137");
        kycUpdateRequest.setCardType(CardType.DRIVERS_LICENCE);
        kycUpdateRequest.setNextOfKin(nextOfKin);
        kycUpdateRequest.setHomeAddress("No 419, mobolowowan Street, Yaba, Lagos");
        kycUpdateRequest.setPassword("12345");

    }


    @Test
    public void saveKyc() {
        kycService.saveKyc(kyc);
    }

    @Test
    public void findKyc() {
        Kyc kyc = kycService.findKyc("63d7142be63cde4ac068e2de").get();
        assertNotNull(kyc);
    }

    @Test
    public void findKycByUserId() {
        Kyc kyc = kycService.findKycByUserId("63d732024c858c17fc6b5ac7");
        assertNotNull(kyc);
    }

}
