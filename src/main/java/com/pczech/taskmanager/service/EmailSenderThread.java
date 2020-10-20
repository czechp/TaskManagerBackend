package com.pczech.taskmanager.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSenderThread implements Runnable {
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage simpleMailMessage;

    public EmailSenderThread(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage) {
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    @Override
    public void run() {
        javaMailSender.send(simpleMailMessage);
    }
}
