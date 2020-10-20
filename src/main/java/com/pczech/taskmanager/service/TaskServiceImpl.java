package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Autowired()
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    @ObjectCreatedAspect()
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks")
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("task id --- " + id));
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    @ObjectModifiedAspect()
    public Task modify(long id, Task task) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        } else
            throw new NotFoundException("task id --- " + id);
    }


    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    @ObjectDeletedAspect()
    public void delete(long id) {
        if (taskRepository.existsById(id))
            taskRepository.deleteById(1L);
        else
            throw new NotFoundException("task id --- " + id);
    }
}
