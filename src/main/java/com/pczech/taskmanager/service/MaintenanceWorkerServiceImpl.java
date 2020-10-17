package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.MaintenanceWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class MaintenanceWorkerServiceImpl implements MaintenanceWorkerService {
    private final MaintenanceWorkerRepository maintenanceWorkerRepository;

    @Autowired()
    public MaintenanceWorkerServiceImpl(MaintenanceWorkerRepository maintenanceWorkerRepository) {
        this.maintenanceWorkerRepository = maintenanceWorkerRepository;
    }

    @Override
    @CacheEvict(cacheNames = {"maintenance-workers", "maintenance-tasks"}, allEntries = true, condition = "#result != null")
    @ObjectCreatedAspect()
    public MaintenanceWorker save(MaintenanceWorker maintenanceWorker) {
        return maintenanceWorkerRepository.save(maintenanceWorker);
    }

    @Override
    @Cacheable("maintenance-workers")
    public List<MaintenanceWorker> findAll() {
        return maintenanceWorkerRepository.findAll();
    }

    @Override
    public MaintenanceWorker findById(long workerId) {
        return maintenanceWorkerRepository.findById(workerId)
                .orElseThrow(() -> new NotFoundException("maintenance worker id --- " + workerId));
    }

    @Override
    @CacheEvict(cacheNames = {"maintenance-workers", "maintenance-tasks"}, allEntries = true)
    @ObjectDeletedAspect()
    public void deleteById(long workerId) {
        if (maintenanceWorkerRepository.existsById(workerId))
            maintenanceWorkerRepository.deleteById(workerId);
        else throw new NotFoundException("maintenance worker id --- " + workerId);
    }

    @Override
    @CacheEvict(cacheNames = {"maintenance-workers", "maintenance-tasks"}, allEntries = true)
    @ObjectModifiedAspect()
    public MaintenanceWorker modify(long id, MaintenanceWorker maintenanceWorker) {
        if (maintenanceWorkerRepository.existsById(id)) {
            maintenanceWorker.setId(id);
            return maintenanceWorkerRepository.save(maintenanceWorker);
        } else
            throw new NotFoundException("maintenance worker id --- " + id);
    }
}
