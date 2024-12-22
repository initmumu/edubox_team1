package com.gamja.edubox_team1.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gamja.edubox_team1.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findAllByRole(String role);

    boolean existsById(long id);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void deleteById(long id);
}
