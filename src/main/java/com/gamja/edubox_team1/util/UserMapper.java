package com.gamja.edubox_team1.util;

import com.gamja.edubox_team1.user.model.dto.*;
import com.gamja.edubox_team1.user.model.entity.User;
import com.gamja.edubox_team1.user.model.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 1. UserSignupRequestDTO → User (회원가입 요청)
    public User toEntity(UserSignupRequestDTO userSignupRequestDTO) {
        User user = new User();
        try {
            String encryptedPassword = passwordEncoder.encode(userSignupRequestDTO.getPassword());
            user.setPassword(encryptedPassword); // 암호화된 비밀번호 설정
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 암호화에 실패했습니다.", e);
        }

        user.setEmail(userSignupRequestDTO.getEmail());
        user.setSignupDate(LocalDateTime.now()); // 가입일을 현재 날짜로 설정
        user.setRole(UserRole.STUDENT); // 기본 역할 설정 (STUDENT)
        return user;
    }

    // 2. UserUpdateRequestDTO → User (회원 정보 수정 요청)
    public User updateEntity(User user, UserUpdateProfileRequestDTO userUpdateProfileRequestDTO) {
        if (userUpdateProfileRequestDTO.getImg() != null) {
            user.setImg(userUpdateProfileRequestDTO.getImg());
        }
        if (userUpdateProfileRequestDTO.getNickname() != null) {
            user.setNickname(userUpdateProfileRequestDTO.getNickname());
        }
        if (userUpdateProfileRequestDTO.getProfileLink() != null) {
            user.setProfileLink(userUpdateProfileRequestDTO.getProfileLink());
        }
        if (userUpdateProfileRequestDTO.getBio() != null) {
            user.setBio(userUpdateProfileRequestDTO.getBio());
        }
        return user;
    }

    // 3. User → UserResponseDTO (응답 데이터 변환)
    public UserResponseDTO toDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setNickname(user.getNickname());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setImg(user.getImg());
        userResponseDTO.setBio(user.getBio());
        userResponseDTO.setProfileLink(user.getProfileLink());

        // LocalDateTime → String 변환 (포맷 지정)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userResponseDTO.setSignupDate(user.getSignupDate().format(formatter));

        return userResponseDTO;
    }
}