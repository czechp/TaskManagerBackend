package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {
    private MaintenanceTaskRepository maintenanceTaskRepository;

    @Autowired()
    public MaintenanceTaskServiceImpl(MaintenanceTaskRepository maintenanceTaskRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
    }

    @Override
    public MaintenanceTask save(MaintenanceTask maintenanceTask) {
        return maintenanceTaskRepository.save(maintenanceTask);
    }

    @Override
    public List<MaintenanceTask> findAll() {
        return maintenanceTaskRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        if (maintenanceTaskRepository.existsById(id))
            maintenanceTaskRepository.deleteById(id);
        else
            throw new NotFoundException("maintenance task id --- " + id);
    }
}
