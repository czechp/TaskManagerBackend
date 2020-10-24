package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.SubTask;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service()
public class SubTaskServiceImpl implements SubTaskService {
    private final SubTaskRepository subTaskRepository;

    @Autowired()
    public SubTaskServiceImpl(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    @ObjectModifiedAspect()
    public SubTask modify(long subTaskId, SubTask subTask) {
        SubTask subtaskOrigin = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new NotFoundException("subtask id --- " + subTaskId));

        if (subTask.getId() == subtaskOrigin.getId()) {
            subTask.setTask(subtaskOrigin.getTask());
            return subTaskRepository.save(subTask);
        } else {
            throw new NotFoundException("subtask id --- " + subTaskId);
        }
    }

    @Override
    @ObjectDeletedAspect()
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    public void deleteById(long subTaskId) {
        if (subTaskRepository.existsById(subTaskId))
            subTaskRepository.deleteById(subTaskId);
        else throw new NotFoundException("subtask id --- " + subTaskId);
    }
}
