package com.pczech.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service()
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender javaMailSender;

    @Autowired()
    public SendEmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async()
    //todo: implement it after handling token
    public void sendVerificationToken(String token, String email) {
        System.out.println("Sending");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("webcoderc@gmail.com");
        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setText("test");
        javaMailSender.send(simpleMailMessage);
    }

}
