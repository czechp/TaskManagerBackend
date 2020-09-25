package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.service.MaintenanceTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/maintenance-tasks")
@Validated()
@CrossOrigin("*")
public class MaintenanceTaskController {
    private MaintenanceTaskService maintenanceTaskService;

    @PostMapping()
    public MaintenanceTask save(@RequestBody @Valid MaintenanceTask maintenanceTask) {
        return maintenanceTaskService.save(maintenanceTask);
    }
}
