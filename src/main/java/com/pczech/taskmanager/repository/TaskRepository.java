package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface TaskRepository extends JpaRepository<Task, Long> {
}
