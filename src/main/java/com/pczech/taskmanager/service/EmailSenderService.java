package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Task;

import javax.servlet.ServletRequest;

public interface EmailSenderService {
    void sendVerificationToken(String token, String email, ServletRequest servletRequest);

    void sendEmailToEverybody(String title, String body);

    void sendEmailToAppUserAboutNewTask(long appUserId, Task task);
}
