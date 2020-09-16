package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired()
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser register(@RequestBody() @Valid() AppUser appUser, Errors errors, ServletRequest servletRequest) {
        return appUserService.register(appUser, errors, servletRequest);
    }

    @GetMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public String activateUserByToken(@RequestParam(value = "token") String token) {
        return appUserService.activateUserByToken(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody() AppUser appUser) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("jwt", appUserService.login(appUser));
        responseBody.put("role", appUserService.getRoleForUser(appUser));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppUser activateUserByAdmin(@PathVariable(value = "id") long id,
                                       @RequestParam(value = "status") String status) {
        return appUserService.activateUserByAdmin(id, status);
    }

    @RequestMapping(path = "/username", method = RequestMethod.HEAD)
    public ResponseEntity existsByUsername(@RequestParam(value = "username") String username) {
        return appUserService.existsByUsername(username) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @RequestMapping(path = "/email", method = RequestMethod.HEAD)
    public ResponseEntity existsByEmail(@RequestParam(value = "email") String email) {
        return appUserService.existsByEmail(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
