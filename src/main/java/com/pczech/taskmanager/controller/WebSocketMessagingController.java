package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Message;
import com.pczech.taskmanager.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller()
public class WebSocketMessagingController {
    private final WebSocketService webSocketService;

    @Autowired()
    public WebSocketMessagingController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/message")
    public void reply(@Payload() Message message) {
        webSocketService.sendToGlobalInfo(message);
    }


}
