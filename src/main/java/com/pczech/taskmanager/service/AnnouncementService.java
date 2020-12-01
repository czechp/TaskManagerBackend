package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.Announcement;
import com.pczech.taskmanager.domain.AnnouncementComment;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> findAll();

    Announcement findById(long announcementId);

    Announcement save(Announcement announcement);

    void deleteById(long announcementId);

    Announcement addComment(long announcementId, AnnouncementComment announcementComment);

    Announcement update(long announcementId, Announcement announcement);
}
