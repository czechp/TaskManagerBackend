package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.service.AnnouncementCommentService;
import com.pczech.taskmanager.validator.annotation.AnnouncementCommentOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController()
@RequestMapping("/api/announcement-comments")
@Validated()
@CrossOrigin("*")
public class AnnouncementCommentController {
    private final AnnouncementCommentService announcementCommentService;

    @Autowired()
    public AnnouncementCommentController(AnnouncementCommentService announcementCommentService) {
        this.announcementCommentService = announcementCommentService;
    }

    @DeleteMapping("/{announcementCommentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AnnouncementCommentOwnerValidator()
    public void deleteById(
            @PathVariable(name = "announcementCommentId") @Min(1L) long announcementCommentId
    ) {
        announcementCommentService.deleteById(announcementCommentId);
    }
}
