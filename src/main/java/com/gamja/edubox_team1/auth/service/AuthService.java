package com.gamja.edubox_team1.auth.service;

import com.gamja.edubox_team1.auth.dto.domain.TokenDto;
import com.gamja.edubox_team1.auth.exception.ExpiredRefreshTokenException;
import com.gamja.edubox_team1.auth.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamja.edubox_team1.auth.dto.entity.AuthUser;
import com.gamja.edubox_team1.auth.repository.AuthRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(String email, String password) {
        Optional<AuthUser> optionalUser = authRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("INVALID_USER_CREDENTIALS");
        }

        AuthUser authUser = optionalUser.get();

        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            throw new BadCredentialsException("INVALID_USER_CREDENTIALS");
        }

        return new TokenDto(jwtTokenProvider.generateAccessToken(authUser),
                jwtTokenProvider.generateRefreshToken(authUser));
    }

    public TokenDto refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }

        long userNo = jwtTokenProvider.getId(refreshToken);

        Optional<AuthUser> optionalUser = authRepository.findById(userNo);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("INVALID_USER_CREDENTIALS");
        }

        AuthUser authUser = optionalUser.get();

        return new TokenDto(jwtTokenProvider.generateAccessToken(authUser),
                null);
    }
}
