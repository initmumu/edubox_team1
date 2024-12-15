package com.gamja.edubox_team1.auth.service;

import com.gamja.edubox_team1.auth.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamja.edubox_team1.auth.dto.entity.User;
import com.gamja.edubox_team1.auth.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findById(username);

        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("Invalid User Credentials");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPwd())) {
            throw new BadCredentialsException("Invalid User Credentials");
        }

//        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return jwtTokenProvider.createToken(user);
    }
}
