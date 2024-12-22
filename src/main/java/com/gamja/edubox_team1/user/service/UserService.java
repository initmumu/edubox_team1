package com.gamja.edubox_team1.user.service;

import com.gamja.edubox_team1.user.exception.UserNotFoundException;
import com.gamja.edubox_team1.user.model.dto.*;
import com.gamja.edubox_team1.user.model.repository.UserRepository;
import com.gamja.edubox_team1.user.model.entity.User;
import com.gamja.edubox_team1.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // 사용자 생성
    public UserResponseDTO createUser(UserSignupRequestDTO userSignupRequestDTO) {
        // DTO → Entity 변환
        User user = userMapper.toEntity(userSignupRequestDTO);

        // 이메일에서 닉네임 추출
        String email = user.getEmail();
        if (email.contains("@")) {
            user.setNickname(email.substring(0, email.indexOf('@')));
        } else {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다: " + email);
        }

        // 사용자 저장
        User savedUser = userRepository.save(user);

        // 저장된 사용자 → DTO 변환 후 반환
        return userMapper.toDto(savedUser);
    }

    // 사용자 단일 조회 (ID로)
    public UserResponseDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + id));
        return userMapper.toDto(user);
    }

    // 사용자 전체 목록 조회
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    // 사용자 이메일로 조회
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. 이메일: " + email));
        return userMapper.toDto(user);
    }

    // 사용자 정보 수정
    public UserResponseDTO updateUserProfile(long id, UserUpdateProfileRequestDTO userUpdateProfileRequestDTO) {
        // 사용자 조회
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + id));

        // Entity → 업데이트
        User updatedUser = userMapper.updateEntity(existingUser, userUpdateProfileRequestDTO);

        // 업데이트된 사용자 저장
        updatedUser = userRepository.save(updatedUser);

        // 저장된 사용자 → DTO 변환 후 반환
        return userMapper.toDto(updatedUser);
    }

    // 사용자 삭제
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // 사용자 존재 여부 확인
    public boolean isUserExists(Long id) {
        return userRepository.existsById(id);
    }
}