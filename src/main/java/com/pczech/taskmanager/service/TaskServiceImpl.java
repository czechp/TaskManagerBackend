package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    @ObjectCreatedAspect()
    public Task save(Task task) {
        return  taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks")
    public List<Task> findAll() {
        return taskRepository.findAll();
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
