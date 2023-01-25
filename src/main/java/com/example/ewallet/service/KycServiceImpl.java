package com.example.ewallet.service;

import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.KycRepository;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.dtos.response.KycResponse;
import com.example.ewallet.dtos.response.KycUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KycServiceImpl implements KycService{

    @Autowired
    private KycRepository kycRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public KycResponse doKyc(KycRequest kycRequest) {
        boolean emailExist = userRepository.findByEmailAddressIgnoreCase(kycRequest.getEmailAddress()).isPresent();
        boolean kycEmailExist = kycRepository.findByEmailAddressIgnoreCase(kycRequest.getEmailAddress()).isPresent();
        User user = userRepository.findByEmailAddressIgnoreCase(kycRequest.getEmailAddress())
                .orElseThrow(()->new RuntimeException("You are not a registered user"));
        setKyc(kycRequest, emailExist, kycEmailExist, user);
        KycResponse kycResponse = new KycResponse();
        kycResponse.setMessage("Thank you for completing the kyc process");
        return kycResponse;
    }

    private void setKyc(KycRequest kycRequest, boolean emailExist, boolean kycEmailExist, User user) {
        if (emailExist && !kycEmailExist) {
            Kyc kyc = Kyc.builder()
                    .bvn(kycRequest.getBvn())
                    .CardName(kycRequest.getCardName())
                    .cardNo(kycRequest.getCardNo())
                    .cvv(kycRequest.getCvv())
                    .emailAddress(kycRequest.getEmailAddress())
                    .nextOfKinFullName(kycRequest.getNextOfKinFullName())
                    .phoneNumber(kycRequest.getPhoneNumber())
                    .relationship(kycRequest.getRelationship())
                    .expireDate(kycRequest.getExpireDate())
                    .cardType(kycRequest.getCardType())
                    .homeAddress(kycRequest.getHomeAddress())
                    .build();
            user.setCompletedKyc(true);
            kyc.setUserId(user.getId());
            kycRepository.save(kyc);
            userRepository.save(user);
        } else {
            throw new RuntimeException("You can no longer complete the kyc process");
        }
    }

    @Override
    public KycUpdateResponse updateDocument(KycUpdateRequest kycUpdateRequest) {
       User user = userRepository.findById(kycUpdateRequest.getUserId())
               .orElseThrow(()-> new RuntimeException("User does not exist"));
       Kyc kyc = kycRepository.findById(kycUpdateRequest.getKycId())
               .orElseThrow(()-> new RuntimeException("No kyc details found"));
       if (user.getPassword().equals(kycUpdateRequest.getPassword())) {
           kyc.setCardType(kycUpdateRequest.getCardType());
           kyc.setCardNo(kycUpdateRequest.getCardNo());
           kyc.setCardName(kycUpdateRequest.getCardName());
           kyc.setCvv(kycUpdateRequest.getCvv());
           kyc.setEmailAddress(kycUpdateRequest.getEmailAddress());
           kyc.setExpireDate(kycUpdateRequest.getExpireDate());
           kyc.setHomeAddress(kycUpdateRequest.getHomeAddress());
           kyc.setRelationship(kycUpdateRequest.getRelationship());
           kyc.setPhoneNumber(kycUpdateRequest.getPhoneNumber());
           kyc.setNextOfKinFullName(kycUpdateRequest.getNextOfKinFullName());
           kycRepository.save(kyc);
       }
        KycUpdateResponse kycUpdateResponse = new KycUpdateResponse();
        kycUpdateResponse.setMessage("Updated");
        return kycUpdateResponse;
    }
}
