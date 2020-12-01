package com.pczech.taskmanager.service;

import com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect;
import com.pczech.taskmanager.aspect.annotation.ObjectDeletedAspect;
import com.pczech.taskmanager.domain.Announcement;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AppUserService appUserService;

    @Autowired()
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, AppUserService appUserService) {
        this.announcementRepository = announcementRepository;
        this.appUserService = appUserService;
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
}
