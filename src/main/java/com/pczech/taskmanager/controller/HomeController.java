package com.pczech.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController()
@RequestMapping("/api")
@CrossOrigin("*")
public class HomeController {

    @GetMapping()
    public ResponseEntity getHelloWorld() {
        HashMap<String, String> result = new HashMap<>();
        result.put("message", "It works");
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
