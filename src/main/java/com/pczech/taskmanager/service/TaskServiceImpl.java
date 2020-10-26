package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.domain.SubTask;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service()
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final AppUserService appUserService;

    @Autowired()
    public TaskServiceImpl(TaskRepository taskRepository, AppUserService appUserService) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
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
            taskRepository.deleteById(id);
        else
            throw new NotFoundException("task id --- " + id);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    @Transactional()
    @ObjectCreatedAspect()
    public Task addGoal(long taskId, Goal goal) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task id --- " + taskId));
        task.addGoal(goal);
        return task;
    }

    @Override
    @Transactional()
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    @ObjectCreatedAspect()
    public Task addTask(long taskId, SubTask subTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task id --- " + taskId));
        task.addSubTask(subTask);
        return task;
    }

    @Override
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    @Transactional()
    @ObjectModifiedAspect()
    public Task addAppUser(long taskId, long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task id --- " + taskId));
        AppUser appUser = appUserService.findById(userId);

        task.addAppUser(appUser);
        return task;
    }

    @Override
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    @Transactional()
    @ObjectModifiedAspect()
    public void deleteAppUserFromTask(long taskId, long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task id --- " + taskId));
        AppUser appUser = appUserService.findById(userId);

        task.removeAppUser(appUser);

    }
}
