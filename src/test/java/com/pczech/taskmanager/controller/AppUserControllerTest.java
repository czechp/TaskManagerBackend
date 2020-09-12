package com.pczech.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.exception.AlreadyExistsException;
import com.pczech.taskmanager.exception.BadDataException;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.exception.UnauthorizedException;
import com.pczech.taskmanager.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
class AppUserControllerTest {
    private final String URL = "/api/users";
    private AppUser appUser;
    private ObjectMapper objectMapper;

    @Autowired()
    private MockMvc mockMvc;

    @MockBean()
    private AppUserService appUserService;


    @BeforeEach()
    public void init() {
        this.appUser = AppUser.builder()
                .username("user")
                .password("user123")
                .role(AppUserRole.USER)
                .email("anyEmail@gmail.com")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test()
    void activateUser() throws Exception {
        //given
        //when
        when(appUserService.activateUserByToken(anyString())).thenReturn("User activated");
        //then
        mockMvc.perform(get(URL + "/activate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", "asdasdsad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User activated"));
    }

    @Test()
    void activateUser_tokenNotFoundTest() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(appUserService)
                .activateUserByToken(anyString());
        //then
        mockMvc.perform(get(URL + "/activate")
                .param("token", "asdasdasd"))
                .andExpect(status().isNotFound());
    }

    @Test()
    void registerTest() throws Exception {
        //given
        //when
        when(appUserService.register(any(), any(), any())).thenReturn(appUser);
        //then
        mockMvc.perform(post(URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("user"));
    }


    @Test()
    void register_AlreadyExistsExceptionTest() throws Exception {
        //given
        //when
        doThrow(AlreadyExistsException.class)
                .when(appUserService)
                .register(any(), any(), any());
        //then
        mockMvc.perform(post(URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isConflict());
    }

    @Test()
    void register_BadRequestTest() throws Exception {
        //given
        //when
        doThrow(BadDataException.class)
                .when(appUserService)
                .register(any(), any(), any());
        //then
        mockMvc.perform(post(URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test()
    void loginTest() throws Exception {
        //given
        //when
        when(appUserService.login(any())).thenReturn("123321");
        when(appUserService.getRoleForUser(any())).thenReturn("USER");
        //then
        mockMvc.perform(post(URL + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("123321"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test()
    void login_unauthorizedTest() throws Exception {
        //given
        //when
        doThrow(UnauthorizedException.class)
                .when(appUserService)
                .login(any());
        //then
        mockMvc.perform(post(URL + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isUnauthorized());
    }

    @Test()
    @WithMockUser(roles = "ADMIN")
    void activateUserByAdminTest() throws Exception {
        //given
        //when
        when(appUserService.activateUserByAdmin(anyLong(), any())).thenReturn(appUser);
        //then
        mockMvc.perform(patch(URL + "/activate/{id}", 4L)
                .param("status", "activate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isOk());
    }

    @Test()
    @WithMockUser(roles = "ADMIN")
    void activateUserByAdmin_IncorrectStatusTest() throws Exception {
        //given
        //when
        doThrow(BadDataException.class)
                .when(appUserService)
                .activateUserByAdmin(anyLong(), any());
        //then
        mockMvc.perform(patch(URL + "/activate/{id}", 4L)
                .param("status", "activate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test()
    @WithMockUser(roles = "ADMIN")
    void activateUserByAdmin_userNotExists() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(appUserService)
                .activateUserByAdmin(anyLong(), any());
        //then
        mockMvc.perform(patch(URL + "/activate/{id}", 4L)
                .param("status", "activate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isNotFound());
    }
}