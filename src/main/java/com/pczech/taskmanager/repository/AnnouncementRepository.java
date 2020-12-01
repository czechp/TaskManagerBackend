package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.Announcement;
import com.pczech.taskmanager.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    boolean existsByIdAndAppUser(long object, AppUser appUser);
}
