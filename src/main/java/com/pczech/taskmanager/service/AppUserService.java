package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;

public interface AppUserService {
    AppUser register(AppUser appUser, Errors errors, ServletRequest servletRequest);

    String activateUserByToken(String token);

    String login(AppUser appUser);

    AppUser activateUserByAdmin(long id, String status);
}
