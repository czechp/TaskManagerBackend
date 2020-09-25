package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.MaintenanceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
}
