package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.TaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController()
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    @GetMapping("/status")
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
