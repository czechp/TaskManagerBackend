package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController()
@RequestMapping("api/comments")
@Validated()
@CrossOrigin("*")
public class CommentController {
    private CommentService commentService;

    @Autowired()
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "commentId") @Min(1L) long commentId){
        commentService.deleteById(commentId);
    }
}
