package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    public void sendVerificationToken(String token, String email, ServletRequest servletRequest) {
        String body = "Aby aktywować konto kliknij  w ----> " +
                createAddress(servletRequest) +
                "/api/users/activate" +
                "?token=" +
                token;
        sendEmail("Aktywacja konta  w menadżerze zadań", body, email);
    }

    @Override
    public void sendEmailToEverybody(String title, String body) {
        List<String> emails = getAllUsersEmails();
        for (String email : emails) {
            sendEmail(title, body, email);
        }
    }

    public void sendEmail(String title, String content, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        new Thread(new EmailSenderThread(javaMailSender, simpleMailMessage)).start();
    }

    @Override
    public void sendEmailToAppUserAboutNewTask(long appUserId, Task task) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new NotFoundException("appUser id --- " + appUserId));
        String content = new StringBuilder()
                .append("Zostałeś dodany do nowego zadania w Managerze Zadań\n")
                .append("Tytuł: " + task.getTitle() + "\n")
                .append("Opis: " + task.getDescription() + "\n")
                .append("Priorytet: " + task.getTaskPriority().getName())
                .toString();

        sendEmail("Nowa praca do wykonania", content, appUser.getEmail());

    }

    @Override
    public void sendEmailToFollowAddresses(List<String> emails, String subject, String content) {
        for (String email : emails) {
            sendEmail(subject, content, email);
        }
    }

    private List<String> getAllUsersEmails() {
        return appUserRepository.findAll()
                .stream()
                .map(AppUser::getEmail)
                .collect(Collectors.toList());
    }

    private String createAddress(ServletRequest servletRequest) {
        return "http://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort();
    }


}
