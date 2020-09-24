package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.MaintenanceWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface MaintenanceWorkerRepository extends JpaRepository<MaintenanceWorker, Long> {
}
