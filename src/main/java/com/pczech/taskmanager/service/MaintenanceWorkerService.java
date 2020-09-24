package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.MaintenanceWorker;

import java.util.List;

public interface MaintenanceWorkerService {
    MaintenanceWorker save(MaintenanceWorker maintenanceWorker);

    List<MaintenanceWorker> findAll();

    MaintenanceWorker findById(long workerId);

    void deleteById(long workerId);
}
