package com.pczech.taskmanager;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component()
@Slf4j()
@Profile("development")
public class DevelopmentData {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired()
    public DevelopmentData(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");
        createUsers();
    }

    private void createUsers() {
        //creating some users
        appUserRepository.save(
                AppUser.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .email("user@gmail.com")
                        .role(AppUserRole.USER)
                        .build()
        );

        appUserRepository.save(
                AppUser.builder()
                        .username("superuser")
                        .password(passwordEncoder.encode("superuser"))
                        .email("superuser@gmail.com")
                        .role(AppUserRole.SUPERUSER)
                        .build()
        );
        appUserRepository.save(
                AppUser.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .role(AppUserRole.ADMIN)
                        .build()
        );
        log.info(appUserRepository.findAll().toString());
    }
}
