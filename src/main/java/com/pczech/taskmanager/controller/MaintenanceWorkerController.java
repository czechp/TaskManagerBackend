package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.service.MaintenanceWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/maintenance-workers")
public class MaintenanceWorkerController {
    private final MaintenanceWorkerService maintenanceWorkerService;

    @Autowired()
    public MaintenanceWorkerController(MaintenanceWorkerService maintenanceWorkerService) {
        this.maintenanceWorkerService = maintenanceWorkerService;
    }

    @PostMapping()
    public MaintenanceWorker save(@RequestBody() @Valid() MaintenanceWorker maintenanceWorker) {
        System.out.println(maintenanceWorker);
        return maintenanceWorkerService.save(maintenanceWorker);
    }
}
