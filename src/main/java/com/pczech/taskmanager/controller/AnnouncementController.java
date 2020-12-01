package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Announcement;
import com.pczech.taskmanager.service.AnnouncementService;
import com.pczech.taskmanager.validator.annotation.AnnouncementOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/announcements")
@CrossOrigin("*")
@Validated()
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @Autowired()
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Announcement> findAll() {
        return announcementService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Announcement task(
            @RequestBody() @Valid() Announcement announcement
    ) {
        return announcementService.save(announcement);
    }

    @DeleteMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AnnouncementOwnerValidator()
    public void deleteById(
            @PathVariable(name = "announcementId") @Min(1L) long announcementId
    ) {
        announcementService.deleteById(announcementId);
    }

    @GetMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.OK)
    public Announcement findById(
            @PathVariable(name = "id") @Min(1L) long announcementId
    ) {
        return announcementService.findById(announcementId);
    }
}
