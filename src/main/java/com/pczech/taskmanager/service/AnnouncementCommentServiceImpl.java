package com.pczech.taskmanager.service;

import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.AnnouncementCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class AnnouncementCommentServiceImpl implements AnnouncementCommentService {
    private final AnnouncementCommentRepository announcementCommentRepository;

    @Autowired()
    public AnnouncementCommentServiceImpl(AnnouncementCommentRepository announcementCommentRepository) {
        this.announcementCommentRepository = announcementCommentRepository;
    }

    @Override
    public void deleteById(long announcementCommentId) {
        if (announcementCommentRepository.existsById(announcementCommentId))
            announcementCommentRepository.deleteById(announcementCommentId);
        else
            throw new NotFoundException("announcementComment id --- " + announcementCommentId);
    }
}
