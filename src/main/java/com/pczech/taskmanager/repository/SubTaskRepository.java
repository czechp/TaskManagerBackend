package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
}
