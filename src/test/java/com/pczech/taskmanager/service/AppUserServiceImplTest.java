package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.exception.BadDataException;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class AppUserServiceImplTest {

    @Autowired()
    private PasswordEncoder passwordEncoder;

    @MockBean()
    private AppUserRepository appUserRepository;

    @MockBean()
    private Errors errors;

    private AppUserService appUserService;
    private AppUser appUser;

    @BeforeEach()
    public void init() {
        this.appUserService = new AppUserServiceImpl(appUserRepository, passwordEncoder);
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
        when(appUserRepository.existsByUsername(anyString())).thenReturn(false);
        when(appUserRepository.save(any())).thenReturn(appUser);
        AppUser result = appUserService.register(appUser, errors);
        //then
        assertNotNull(result);
    }

    @Test()
    void registerTest_hasErrors() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        when(appUserRepository.existsByUsername(anyString())).thenReturn(false);
        //then
        assertThrows(BadDataException.class, () -> appUserService.register(appUser, errors));
    }
}

