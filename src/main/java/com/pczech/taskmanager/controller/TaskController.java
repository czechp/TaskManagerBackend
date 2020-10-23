package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.domain.SubTask;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.domain.TaskStatus;
import com.pczech.taskmanager.repository.GoalRepository;
import com.pczech.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

@RestController()
@RequestMapping("/api/tasks")
@CrossOrigin("*")
@Validated()
public class TaskController {
    private TaskService taskService;


    @Autowired()
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Task save(@RequestBody @Valid Task task) {
        return taskService.save(task);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task findById(@PathVariable(name = "id") @Min(1L) long id){
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task modify(@PathVariable(name = "id") @Min(1L) long id,
                       @RequestBody() @Valid() Task task ){
        return taskService.modify(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") @Min(1L) long id){
        taskService.delete(id);
    }

    @PostMapping("/{taskId}/goals")
    public Task addGoal(@PathVariable(value = "taskId") @Min(1L) long taskId,
                        @RequestBody() Goal goal){
        return taskService.addGoal(taskId, goal);
    }


    @PostMapping("/{taskId}/subtasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addSubTask(
            @PathVariable(name = "taskId") @Min(1L) long taskId,
            @RequestBody() @Valid() SubTask subTask
            ){
        return taskService.addTask(taskId, subTask);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getStatus() {
        List<Map<String, String>> body = new ArrayList<>();
        Arrays.stream(TaskStatus.values())
                .forEach(x -> {
                    HashMap<String, String> status = new HashMap<>();
                    status.put("status", x.toString());
                    body.add(status);
                });
        return ResponseEntity.ok(body);
    }
}
