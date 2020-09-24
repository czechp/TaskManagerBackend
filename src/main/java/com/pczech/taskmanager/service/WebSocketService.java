package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Message;

public interface WebSocketService {
    public void sendToGlobalInfo(Message message);
}
