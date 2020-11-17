package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Comment;
import com.pczech.taskmanager.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired()
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
