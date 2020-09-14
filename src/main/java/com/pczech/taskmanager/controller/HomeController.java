package com.pczech.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class HomeController {

    @GetMapping()
    public String getHelloWorld() {
        return "Hello world";
    }
}
