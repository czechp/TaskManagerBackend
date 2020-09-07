package com.pczech.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Service()
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired()
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async()
    public void sendVerificationToken(String token, String email, ServletRequest servletRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Aktywacja konta  w menadżerze zadań");
        simpleMailMessage.setText(
                "Aby aktywować konto kliknij  w ----> " +
                        createAddres(servletRequest) +
                        "?token=" +
                        token);
        javaMailSender.send(simpleMailMessage);
    }

    private String createAddres(ServletRequest servletRequest) {
        return "http://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort();
    }


}
