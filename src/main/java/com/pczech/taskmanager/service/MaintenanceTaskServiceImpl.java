package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;

public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {
    private MaintenanceTaskRepository maintenanceTaskRepository;

    @Override
    public MaintenanceTask save(MaintenanceTask maintenanceTask) {
        return maintenanceTaskRepository.save(maintenanceTask);
    }
}
