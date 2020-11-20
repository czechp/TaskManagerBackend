package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface CommentRepository extends JpaRepository<TaskComment, Long> {
}
