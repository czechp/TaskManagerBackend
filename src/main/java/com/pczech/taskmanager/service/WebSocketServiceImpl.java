package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service()
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate template;

    @Autowired()
    public WebSocketServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void sendToGlobalInfo(@Payload() Message message) {
        template.convertAndSend("/messaging/info", message);
    }
}
