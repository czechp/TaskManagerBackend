package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.AnnouncementComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, Long> {
    boolean existsByIdAndOwner(long object, String owner);

}
