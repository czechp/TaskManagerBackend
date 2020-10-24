package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.SubTask;

public interface SubTaskService {
    SubTask modify(long subTaskId, SubTask subTask);

    void deleteById(long subTaskId);
}
