package com.gamja.edubox_team1.auth.repository;

import com.gamja.edubox_team1.auth.dto.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findById(Long no);
    Optional<AuthUser> findByEmail(String email);
}
