package com.example.ewallet.service;

import com.example.ewallet.dtos.request.ResendTokenRequest;
import com.example.ewallet.dtos.request.SetPasswordRequest;
import com.example.ewallet.service.email.EmailSenderService;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.UserRepository;
import com.example.ewallet.dtos.request.ConfirmTokenRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private UserService userService;
    private ConfirmationTokenService confirmationTokenService;
    private EmailSenderService emailSenderService;


    public RegistrationServiceImpl(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService ){
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    public RegistrationResponse register(RegistrationRequest registrationRequest) throws MessagingException {
        boolean emailExist = userService.findUser(registrationRequest.getEmailAddress()).isPresent();
        if (emailExist) throw new IllegalStateException("Email Address Already Exist");
        User user = new User();
        user.setEmailAddress(registrationRequest.getEmailAddress());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setPassword(registrationRequest.getPassword());
        String token = userService.createAccount(user);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setStatus(HttpStatus.CREATED);
        registrationResponse.setToken(token);
        emailSenderService.send(registrationRequest.getEmailAddress(),buildEmail(registrationRequest.getEmailAddress(),token));
        return registrationResponse;
    }
    public String confirmToken(ConfirmTokenRequest confirmationToken){
        var token = confirmationTokenService.getConfirmationToken(confirmationToken.getToken())
                .orElseThrow(()-> new IllegalStateException("Token does not exist"));

        if (token.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }
        confirmationTokenService.setConfirmed(token.getToken());
        userService.enableUser(confirmationToken.getEmailAddress());
        return "confirmed";
    }
    @Override
    public String resendToken(ResendTokenRequest request) throws MessagingException {
        var foundUser = userService.findUser(request.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("Email does not exist"));

            var generatedToken = userService.generateToken(foundUser);
            emailSenderService.send(request.getEmailAddress(),buildEmail(request.getEmailAddress(),generatedToken));
            return generatedToken;
       }

       @Override
    public String setPassword(SetPasswordRequest passwordRequest){
        User user = userService.findUser(passwordRequest.getEmailAddress())
                .orElseThrow(()->new RuntimeException("Email does not exist"));
        user.setPassword(passwordRequest.getNewPassword());
        userService.saveUser(user);
        return "Password set Successfully";
    }


    protected String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" +
                " <p>"+ link + " </p> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
