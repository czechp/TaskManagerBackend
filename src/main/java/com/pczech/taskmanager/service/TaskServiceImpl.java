package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class TaskServiceImpl implements TaskService{
    private TaskRepository taskRepository;

    @Autowired()
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return  taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public Task findById(long id) {
        return null;
    }

    @Override
    public Task modify(long id, Task task) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
