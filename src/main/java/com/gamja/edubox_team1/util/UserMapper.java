package com.gamja.edubox_team1.util;

import com.gamja.edubox_team1.user.model.dto.UserResponseDTO;
import com.gamja.edubox_team1.user.model.dto.UserRequestDTO;
import com.gamja.edubox_team1.user.model.entity.User;

import java.util.Date;

public class UserMapper {

    // 1. UserRequestDTO → User
    // 사용자 가입 요청
    public static User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setId(userRequestDTO.getId());
        // SHA-256으로 변환하여 저장
        String encryptedPassword = PasswordEncryptor.encryptPassword(userRequestDTO.getPassword());
        user.setPassword(encryptedPassword);   // 암호화된 비밀번호 설정
        user.setNickname(userRequestDTO.getNickname());
        user.setEmail(userRequestDTO.getEmail());
        user.setBio(userRequestDTO.getBio());
        user.setSignupDate(new Date());         // 가입일은 현재 날짜로 설정
        return user;
    }

    // 2. User → UserDTO
    // 데이터베이스에서 가져온 User entity -> 응답용 DTO로 변환
    public static UserResponseDTO toDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setNo(user.getNo());            // 엔티티의 no를 DTO의 no로 설정
        userResponseDTO.setId(user.getId());
        userResponseDTO.setNickname(user.getNickname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setImg(user.getImg());
        userResponseDTO.setBio(user.getBio());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setSignupDate(user.getSignupDate().toString()); // Date → String 변환
        return userResponseDTO;
    }
}
