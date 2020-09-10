package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.CurrentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface CurrentTaskRepository extends JpaRepository<CurrentTask, Long> {
}
