package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.exception.AlreadyExistsException;
import com.pczech.taskmanager.exception.BadDataException;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class AppUserServiceImplTest {

    @MockBean()
    ServletRequest servletRequest;
    @Autowired()
    private PasswordEncoder passwordEncoder;
    @MockBean()
    private AppUserRepository appUserRepository;
    @MockBean()
    private Errors errors;
    @MockBean()
    private EmailSenderService emailSenderService;
    private AppUserService appUserService;
    private AppUser appUser;

    @BeforeEach()
    public void init() {
        this.appUserService = new AppUserServiceImpl(appUserRepository, passwordEncoder, emailSenderService);
        this.appUser = AppUser.builder()
                .id(1L)
                .username("user")
                .password("user")
                .email("anyEmail@gmail.com")
                .tokenValidation(true)
                .adminApproved(true)
                .build();


    }

    @Test()
    void registerTest() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        when(appUserRepository.save(any())).thenReturn(appUser);
        AppUser result = appUserService.register(appUser, errors, servletRequest);
        //then
        assertNotNull(result);
    }

    @Test()
    void register_dataAlreadyExistsTest() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        //then
        assertThrows(BadDataException.class, () -> appUserService.register(appUser, errors, servletRequest));
    }

    @Test()
    void register_hasErrorsTest() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);
        //then
        assertThrows(AlreadyExistsException.class, () -> appUserService.register(appUser, errors, servletRequest));
    }

    @Test()
    void activateUserByTokenTest() {
        //given
        String token = "Some token";
        //when
        when(appUserRepository.findByToken(anyString())).thenReturn(Optional.of(appUser));
        String result = appUserService.activateUserByToken(token);
        //then
        assertEquals("User activated", result);
    }

    @Test()
    void activeUserByToken_userNotFound() {
        //given
        String token = "Some token";
        //when
        when(appUserRepository.findByToken(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> appUserService.activateUserByToken(token));
    }
}

