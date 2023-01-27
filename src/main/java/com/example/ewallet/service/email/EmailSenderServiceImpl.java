package com.example.ewallet.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    JavaMailSender javaMailSender;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String toEmail, String email) throws MessagingException {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setFrom("AbeebRonike@gmail.com");
            mimeMessageHelper.setSubject("Confirm your Email Address");
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("problem1 "+ e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e) {
            log.info("problem 2 "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
