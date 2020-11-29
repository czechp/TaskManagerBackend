package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Task;

import javax.servlet.ServletRequest;
import java.util.List;

public interface EmailSenderService {
    void sendVerificationToken(String token, String email, ServletRequest servletRequest);

    void sendEmailToEverybody(String title, String body);

    void sendEmailToAppUserAboutNewTask(long appUserId, Task task);

    void sendEmailToFollowAddresses(List<String> emails, String subject, String content);
}
