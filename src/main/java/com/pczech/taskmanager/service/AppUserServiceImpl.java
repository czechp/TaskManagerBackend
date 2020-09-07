package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.exception.AlreadyExistsException;
import com.pczech.taskmanager.exception.BadDataException;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;

@Service()
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    @Autowired()
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public AppUser register(AppUser appUser, Errors errors, ServletRequest servletRequest) {
        if (!errors.hasErrors()) {
            if (!appUserRepository.existsByUsernameOrEmail(appUser.getUsername(), appUser.getEmail())) {
                appUser.generateToken();
                appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
                AppUser result = appUserRepository.save(appUser);
                result.setPassword("");
                emailSenderService.sendVerificationToken(appUser.getToken(), appUser.getEmail(), servletRequest);
                return result;
            } else {
                throw new AlreadyExistsException("username --- " + appUser.getUsername() + " or email --- " + appUser.getEmail());
            }
        } else
            throw new BadDataException(appUser.getUsername());

    }
}
