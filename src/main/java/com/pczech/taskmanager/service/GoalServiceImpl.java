package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service()
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;


    @Autowired()
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    @CacheEvict(value = {"tasks"}, allEntries = true)
    @ObjectModifiedAspect()
    public Goal modify(long id, Goal goal) {
        Goal result = goalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("goal id --- " + id));
        if (goal.getId() == result.getId()) {
            goal.setTask(result.getTask());
            return goalRepository.save(goal);
        } else {
            throw new NotFoundException("goal id --- " + id);
        }
    }

    @Override
    @CacheEvict(value = {"tasks"}, allEntries = true)
    @ObjectDeletedAspect()
    public void deleteById(long id) {
        if (goalRepository.existsById(id))
            goalRepository.deleteById(id);
        else
            throw new NotFoundException("goal id --- " + id);
    }
}
