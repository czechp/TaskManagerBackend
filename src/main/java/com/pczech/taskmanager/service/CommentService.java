package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Comment;

import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);
    public List<Comment> findAll();
    public Comment findById(long id);
    public void deleteById(long id);
}
