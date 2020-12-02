package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectModifiedAspect;
import com.pczech.taskmanager.domain.Announcement;
import com.pczech.taskmanager.domain.AnnouncementComment;
import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service()
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AppUserService appUserService;
    private final ValidatorService validatorService;

    @Autowired()
    public AnnouncementServiceImpl(
            AnnouncementRepository announcementRepository,
            AppUserService appUserService,
            ValidatorService validatorService) {
        this.announcementRepository = announcementRepository;
        this.appUserService = appUserService;
        this.validatorService = validatorService;
    }

    @Override
    @Cacheable(cacheNames = {"announcements"})
    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement findById(long announcementId) {
        return this.announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("announcement id --- " + announcementId));
    }

    @Override
    @CacheEvict(allEntries = true, cacheNames = {"announcements"})
    @ObjectCreatedAspect()
    public Announcement save(Announcement announcement) {
        announcement.setId(0L);
        announcement.addAppUser(appUserService.getCurrentUser());
        return announcementRepository.save(announcement);
    }


    @Override
    @CacheEvict(allEntries = true, cacheNames = {"announcements"})
    @ObjectDeletedAspect()
    public void deleteById(long announcementId) {
        if (announcementRepository.existsById(announcementId))
            this.announcementRepository.deleteById(announcementId);
        else
            throw new NotFoundException("announcement id  --- " + announcementId);
    }

    @Override
    @CacheEvict(cacheNames = {"announcements"}, allEntries = true)
    @ObjectModifiedAspect()
    @Transactional()
    public Announcement addComment(long announcementId, AnnouncementComment announcementComment) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("announcement id --- " + announcementId));
        AppUser currentUser = appUserService.getCurrentUser();
        announcementComment.setOwner(currentUser.getUsername());
        announcementComment.setFullName(currentUser.getFullName());
        validatorService.validateWithException(announcementComment);
        announcement.addComment(announcementComment);
        return announcement;
    }

    @Override
    @CacheEvict(cacheNames = {"announcements"}, allEntries = true)
    @ObjectModifiedAspect()
    public Announcement update(long announcementId, Announcement announcement) {
        Announcement origin = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("announcement id --- " + announcementId));

        announcement.setId(origin.getId());
        announcement.setAppUser(origin.getAppUser());
        announcement.setComments(origin.getComments());

        return announcementRepository.save(announcement);
    }
}
