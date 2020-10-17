package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GoalRepository extends JpaRepository<Goal, Long> {
}
