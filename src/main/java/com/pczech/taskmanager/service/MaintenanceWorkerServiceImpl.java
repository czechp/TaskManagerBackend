package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.repository.MaintenanceWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class MaintenanceWorkerServiceImpl implements MaintenanceWorkerService {
    private final MaintenanceWorkerRepository maintenanceWorkerRepository;

    @Autowired()
    public MaintenanceWorkerServiceImpl(MaintenanceWorkerRepository maintenanceWorkerRepository) {
        this.maintenanceWorkerRepository = maintenanceWorkerRepository;
    }

    @Override
    public MaintenanceWorker save(MaintenanceWorker maintenanceWorker) {
        return maintenanceWorkerRepository.save(maintenanceWorker);
    }
}
