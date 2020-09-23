package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class AppUserServiceImplTest {
    @Autowired()
    private AuthenticationManager authenticationManager;
    @Autowired()
    private PasswordEncoder passwordEncoder;
    @Autowired()
    private JwtTokenService jwtTokenService;
    @MockBean()
    ServletRequest servletRequest;
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
        this.appUserService = new AppUserServiceImpl(appUserRepository, passwordEncoder, emailSenderService, authenticationManager, jwtTokenService);
        this.appUser = AppUser.builder()
                .id(1L)
                .username("user")
                .password("user")
                .role(AppUserRole.USER)
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

    @Test()
    void activateUserActivateByAdminTest(){
        //given
        long id = 4L;
        String status = "activate";
        appUser.setAdminApproved(false);
        //when
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(appUser));
        when(appUserRepository.save(any())).thenReturn(appUser);
        AppUser appUser = appUserService.activateUserByAdmin(id, status);
        //then
        assertNotNull(appUser);
        assertTrue(appUser.isAdminApproved());
    }

    @Test()
    void activateUserByAdminDeactivateTest(){
        //given
        long id = 4L;
        String status = "deactivate";
        appUser.setAdminApproved(true);
        //when
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(appUser));
        when(appUserRepository.save(any())).thenReturn(appUser);
        AppUser appUser = appUserService.activateUserByAdmin(id, status);
        //then
        assertNotNull(appUser);
        assertFalse(appUser.isAdminApproved());
    }

    @Test()
    void activateUserByAdmin_IncorrectStatus(){
        //given
        long id = 4L;
        String status = "123123123";
        //when
        //then
        assertThrows(BadDataException.class, ()->appUserService.activateUserByAdmin(id, status));
    }

    @Test()
    void activateUserByAdmin_UserNotExists(){
        //given
        long id = 4L;
        String status = "activate";
        //when
        when(appUserRepository.findById(id)).thenReturn(Optional.empty());
        //
        assertThrows(NotFoundException.class, ()->appUserService.activateUserByAdmin(id, status));
    }

    @Test()
    void getRoleForUserTest(){
        //given
        //when
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(appUser));
        String result = appUserService.getRoleForUser(appUser);
        //then
        assertTrue(result.equals(appUser.getRole().toString()));
    }

    @Test()
    void getRoleForUser_userNotExistsTest(){
        //given
        //when
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()->appUserService.getRoleForUser(appUser));
    }

}

