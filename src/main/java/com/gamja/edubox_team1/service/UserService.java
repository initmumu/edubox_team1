package com.gamja.edubox_team1.service;

import com.gamja.edubox_team1.exception.UserNotFoundException;
import com.gamja.edubox_team1.model.dto.UserResponseDTO;
import com.gamja.edubox_team1.model.dto.UserRequestDTO;
import com.gamja.edubox_team1.model.repository.UserRepository;
import com.gamja.edubox_team1.model.entity.User;
import com.gamja.edubox_team1.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 사용자 생성
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = UserMapper.toEntity(userRequestDTO); // DTO를 엔티티로 변환
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser); // 저장된 엔티티를 다시 DTO로 변환
    }

    // 사용자 단일 조회 (ID로)
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + id));
        return UserMapper.toDto(user);
    }

    // 사용자 전체 목록 조회
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // 사용자 이메일로 조회
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. 이메일: " + email));
        return UserMapper.toDto(user);
    }

    // 사용자 수정
    public UserResponseDTO updateUser(String id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + id));

        // 필드 업데이트
        existingUser.setNickname(userRequestDTO.getNickname());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setBio(userRequestDTO.getBio());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDto(updatedUser);
    }

    // 사용자 삭제
    public void deleteUser(String id) {
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
