package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.SubTask;
import com.pczech.taskmanager.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController()
@RequestMapping("/api/subtasks")
@CrossOrigin("*")
public class SubTaskController {
    private SubTaskService subTaskService;

    @Autowired()
    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @PutMapping("/{subtaskId}")
    @ResponseStatus(HttpStatus.OK)
    public SubTask modify(
            @PathVariable(name = "subtaskId") @Min(1L) long subTaskId,
            @RequestBody() @Valid() SubTask subTask
    ){
        return subTaskService.modify(subTaskId, subTask);
    }

    @DeleteMapping("/{subtaskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable(name = "subtaskId") @Min(1L) long subTaskId
    ){
        subTaskService.deleteById(subTaskId);
    }

}
