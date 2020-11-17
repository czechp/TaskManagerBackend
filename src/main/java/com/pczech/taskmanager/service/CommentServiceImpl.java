package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.domain.Comment;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(cacheNames = {"tasks"}, allEntries = true)
    @ObjectDeletedAspect()
    public void deleteById(long id) {
        if(commentRepository.existsById(id))
            commentRepository.deleteById(id);
        else
            throw new NotFoundException("comment id --- " + id);
    }
}
