package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
class AppUserControllerTest {
    private final String URL = "/api/users";

    @Autowired()
    private MockMvc mockMvc;

    @MockBean()
    private AppUserService appUserService;

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

}