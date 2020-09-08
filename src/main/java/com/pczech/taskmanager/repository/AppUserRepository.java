package com.pczech.taskmanager.repository;

import com.pczech.taskmanager.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    Optional<AppUser> findByToken(String token);

    Optional<AppUser> findByUsername(String s);
}
