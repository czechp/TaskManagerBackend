package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Goal;

public interface GoalService {

    Goal modify(long id, Goal goal);

    void deleteById(long id);
}
