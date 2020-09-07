package com.pczech.taskmanager;

import com.pczech.taskmanager.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component()
@Slf4j()
@Profile("development")
public class Setup {

    @Autowired()
    public Setup(SendEmailService sendEmailService) {

    }


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");


    }
}
