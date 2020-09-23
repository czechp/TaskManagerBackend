package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.service.AppUserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin("*")
@Validated()
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

    @PatchMapping("/admin/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
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

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void deleteUserById(@PathVariable(value = "id") @Min(1) long id,
                               @RequestBody() @Valid() AppUser appUser, Errors errors) {
        appUserService.deleteUserById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public List<AppUser> findAll() {
        return appUserService.findAll();
    }

    @GetMapping("/roles")
    public ResponseEntity findAllUserStatus() {
        return new ResponseEntity(appUserService.finaAllUserRoles(), HttpStatus.OK);
    }

    @PatchMapping("/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void changeStatus(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "role") @Length(min = 3, max = 10) String status
    ) {
        appUserService.modifyRole(id, status);
    }

}
