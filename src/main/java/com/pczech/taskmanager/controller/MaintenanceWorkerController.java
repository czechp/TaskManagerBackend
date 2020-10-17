package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.service.MaintenanceWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/maintenance-workers")
@Validated()
@CrossOrigin("*")
public class MaintenanceWorkerController {
    private final MaintenanceWorkerService maintenanceWorkerService;

    @Autowired()
    public MaintenanceWorkerController(MaintenanceWorkerService maintenanceWorkerService) {
        this.maintenanceWorkerService = maintenanceWorkerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceWorker save(@RequestBody() @Valid() MaintenanceWorker maintenanceWorker) {
        return maintenanceWorkerService.save(maintenanceWorker);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MaintenanceWorker> findAll() {
        return maintenanceWorkerService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MaintenanceWorker findById(@PathVariable(value = "id") @Min(1) long workerId) {
        return maintenanceWorkerService.findById(workerId);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MaintenanceWorker modifyMaintenanceWorker(@RequestBody() @Valid() MaintenanceWorker maintenanceWorker,
                                                     @PathVariable(value = "id") @Min(1) long id) {
        return maintenanceWorkerService.modify(id, maintenanceWorker);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER", "ROLE_DIRECTOR"})
    public void deleteUserById(@PathVariable(value = "id") @Min(1) long workerId) {
        maintenanceWorkerService.deleteById(workerId);
    }
}
