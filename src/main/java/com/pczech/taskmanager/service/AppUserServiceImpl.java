package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.exception.BadDataException;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service()
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired()
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser register(AppUser appUser, Errors errors) {
        if (!errors.hasErrors()
                && !appUserRepository.existsByUsername(appUser.getUsername())) {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            AppUser result = appUserRepository.save(appUser);
            result.setPassword("");
            return appUser;

        } else
            throw new BadDataException(appUser.getUsername());

    }
}
