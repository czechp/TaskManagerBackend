package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.service.MaintenanceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/maintenance-tasks")
@Validated()
@CrossOrigin("*")
public class MaintenanceTaskController {
    private MaintenanceTaskService maintenanceTaskService;

    @Autowired()
    public MaintenanceTaskController(MaintenanceTaskService maintenanceTaskService) {
        this.maintenanceTaskService = maintenanceTaskService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceTask save(@RequestBody @Valid MaintenanceTask maintenanceTask) {
        return maintenanceTaskService.save(maintenanceTask);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MaintenanceTask> findAll() {
        return maintenanceTaskService.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(value = "id") @Min(1) long id) {
        maintenanceTaskService.deleteById(id);
    }

}
