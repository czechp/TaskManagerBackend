package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;

public interface AppUserService {
    AppUser register(AppUser appUser, Errors errors, ServletRequest servletRequest);

    String activateUserByToken(String token);

    String login(AppUser appUser);

    AppUser activateUserByAdmin(long id, String status);

    String getRoleForUser(AppUser appUser);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteUserById(long id);

    List<AppUser> findAll();

    List<HashMap<String, String>> finaAllUserRoles();

    AppUser modifyRole(long id, String status);

    AppUser findByUsername(String username);

    AppUser findById(long userId);

}
