package com.gamja.edubox_team1.auth.service;

import com.gamja.edubox_team1.auth.dto.entity.User;
import com.gamja.edubox_team1.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSetupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSetupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String username, String rawPassword, String role) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setId(username);
        user.setPwd(encodedPassword);
        user.setRole(role);
        userRepository.save(user);
    }
}