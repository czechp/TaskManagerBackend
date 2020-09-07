package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import org.springframework.validation.Errors;

public interface AppUserService {
    AppUser register(AppUser appUser, Errors errors);
}
