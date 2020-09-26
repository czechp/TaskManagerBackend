package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service()
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {
    private final MaintenanceTaskRepository maintenanceTaskRepository;

    @Autowired()
    public MaintenanceTaskServiceImpl(MaintenanceTaskRepository maintenanceTaskRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
    }

    @Override
    @CacheEvict(value = {"users", "maintenance-workers", "maintenance-tasks"}, allEntries = true, condition = "#result != null")
    @ObjectCreatedAspect()
    public MaintenanceTask save(MaintenanceTask maintenanceTask) {
        return maintenanceTaskRepository.save(maintenanceTask);
    }

    @Override
    @Cacheable(value = "maintenance-tasks")
    public List<MaintenanceTask> findAll() {
        return maintenanceTaskRepository.findAll();
    }

    @Override
    @CacheEvict(value = {"users", "maintenance-workers", "maintenance-tasks"}, allEntries = true)
    @Transactional()
    public void deleteById(long id) {
        MaintenanceTask maintenanceTask = maintenanceTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("maintenance task id --- " + id));
        maintenanceTask
                .getMaintenanceWorker()
                .getMaintenanceTasks()
                .remove(maintenanceTask);
    }

    @Override
    public MaintenanceTask findById(long id) {
        return maintenanceTaskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("maintenance id --- " + id));
    }

    @Override
    @CacheEvict(value = {"users", "maintenance-workers", "maintenance-tasks"}, allEntries = true, condition = "#result != null")
    public MaintenanceTask modify(MaintenanceTask maintenanceTask, long id) {
        if(maintenanceTaskRepository.existsById(id)){
            maintenanceTask.setId(id);
            return maintenanceTaskRepository.save(maintenanceTask);
        }else
            throw new NotFoundException("maintenance task id --- " + id);
    }
}
