package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.domain.Task;

import java.util.List;

public interface TaskService {
    public Task save(Task task);
    public List<Task> findAll();
    public Task findById(long id);
    public Task modify(long id, Task task);
    public void delete(long id);
    Task addGoal(long taskId, Goal goal);
}
