package com.shoppingmall.otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("taejun050413@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.debug("otp successfully sent");
    }
}