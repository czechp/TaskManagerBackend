package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller()
public class WebSocketMessagingController {
    @MessageMapping("/message")
    @SendTo("/messaging/info")
    public Message reply(@Payload() Message message) {
        System.out.println(message);
        return message;
    }


}
