package com.gamja.edubox_team1.auth.repository;

import com.gamja.edubox_team1.auth.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String username);
}
