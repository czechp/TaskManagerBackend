package com.pczech.taskmanager.service;

import javax.servlet.ServletRequest;

public interface EmailSenderService {
    void sendVerificationToken(String token, String email, ServletRequest servletRequest);
}
