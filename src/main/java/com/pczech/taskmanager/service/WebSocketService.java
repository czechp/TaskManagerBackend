package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Message;

public interface WebSocketService {
    void sendToGlobalInfo(Message message);
}
