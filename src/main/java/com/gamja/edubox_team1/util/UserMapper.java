package com.gamja.edubox_team1.util;

import com.gamja.edubox_team1.model.dto.UserDTO;
import com.gamja.edubox_team1.model.dto.UserRequestDTO;
import com.gamja.edubox_team1.model.entity.User;

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
    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNo(user.getNo());            // 엔티티의 no를 DTO의 no로 설정
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setEmail(user.getEmail());
        userDTO.setImg(user.getImg());
        userDTO.setBio(user.getBio());
        userDTO.setRole(user.getRole());
        userDTO.setSignupDate(user.getSignupDate().toString()); // Date → String 변환
        return userDTO;
    }
}
