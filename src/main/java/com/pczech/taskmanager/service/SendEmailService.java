package com.pczech.taskmanager.service;

public interface SendEmailService {
    void sendVerificationToken(String token, String email);
}
