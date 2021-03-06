package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.domain.SubTask;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.domain.TaskStatus;
import com.pczech.taskmanager.service.TaskService;
import com.pczech.taskmanager.validator.annotation.TaskOwnerValidator;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    private final TaskService taskService;


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
    public Task findById(@PathVariable(name = "id") @Min(1L) long id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @TaskOwnerValidator()
    public Task modify(@PathVariable(name = "id") @Min(1L) long id,
                       @RequestBody() @Valid() Task task) {
        return taskService.modify(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") @Min(1L) long id) {
        taskService.delete(id);
    }

    @PostMapping("/{taskId}/goals")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addGoal(@PathVariable(value = "taskId") @Min(1L) long taskId,
                        @RequestBody() Goal goal) {
        return taskService.addGoal(taskId, goal);
    }

    @PostMapping("/{taskId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addComment(
            @PathVariable(name = "taskId") @Min(1) long taskId,
            @RequestParam(name = "content") @Length(min = 5, max = 255) String content
    ){
        return taskService.addComment(taskId, content);
    }


    @PostMapping("/{taskId}/subtasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addSubTask(
            @PathVariable(name = "taskId") @Min(1L) long taskId,
            @RequestBody() @Valid() SubTask subTask
    ) {
        return taskService.addTask(taskId, subTask);
    }


    @PostMapping("/{taskId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Task addAppUser(
        @PathVariable(name = "taskId") @Min(1L) long taskId,
        @PathVariable(name = "userId") @Min(1L) long userId
    ){
        return taskService.addAppUser(taskId, userId);
    }

    @Secured(value = {"ROLE_ADMIN","ROLE_SUPERUSER"})
    @DeleteMapping("/{taskId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppUserFromTask(
            @PathVariable(name = "taskId") @Min(1L) long taskId,
            @PathVariable(name = "userId") @Min(1L) long userId
    ){
        taskService.deleteAppUserFromTask(taskId, userId);
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

    @PutMapping("/{taskId}/finish")
    public  Task finishTask(
            @PathVariable(name = "taskId") @Min(1L) long taskId,
            @RequestBody() List<String> emails,
            @RequestParam(name = "conclusion") @Length(min=5) String conclusion
    ){
        return taskService.finishTask(taskId, emails, conclusion);
    }
}
