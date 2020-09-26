package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service()
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final AppUserRepository appUserRepository;

    @Autowired()
    public EmailSenderServiceImpl(JavaMailSender javaMailSender, AppUserRepository appUserRepository) {
        this.javaMailSender = javaMailSender;
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Async()
    public void sendVerificationToken(String token, String email, ServletRequest servletRequest) {
        String body = "Aby aktywować konto kliknij  w ----> " +
                createAddress(servletRequest) +
                "/api/users/activate" +
                "?token=" +
                token;
        sendEmail("Aktywacja konta  w menadżerze zadań", body, email);
    }

    private String createAddress(ServletRequest servletRequest) {
        return "http://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort();
    }


    @Override
    public void sendEmailToEverybody(String title, String body) {
        List<String> emails = getAllUsersEmails();
        for (String email : emails) {
            sendEmail(title, body, email);
        }
    }

    private List<String> getAllUsersEmails() {
        return appUserRepository.findAll()
                .stream()
                .map(AppUser::getEmail)
                .collect(Collectors.toList());
    }

    @Async()
    public void sendEmail(String title, String content, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
