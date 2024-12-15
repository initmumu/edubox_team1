package com.gamja.edubox_team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamja.edubox_team1.model.dto.UserResponseDTO;
import com.gamja.edubox_team1.model.dto.UserRequestDTO;
import com.gamja.edubox_team1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private ObjectMapper objectMapper;

    @Value("${spring.datasource.url}")
    String dbUrl;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    private String password;

    @BeforeEach
    void setUp() {
        // MockMvc 수동 구성
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper(); // ObjectMapper 직접 생성
    }

    // 회원가입 테스트
    @Test
    void createUserTest() throws Exception {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setId("user123");
        requestDTO.setPassword("securePassword123!");
        requestDTO.setNickname("john_doe");
        requestDTO.setEmail("john@example.com");
        requestDTO.setBio("Hello World!");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setNo(1L);
        responseDTO.setId("user123");
        responseDTO.setNickname("john_doe");
        responseDTO.setEmail("john@example.com");
        responseDTO.setBio("Hello World!");
        responseDTO.setSignupDate(new Date().toString());

        Mockito.when(userService.createUser(any(UserRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user123"))
                .andExpect(jsonPath("$.nickname").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // 특정 회원 정보 조회 테스트
    @Test
    void getUserTest() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setNo(1L);
        responseDTO.setId("user123");
        responseDTO.setNickname("john_doe");
        responseDTO.setEmail("john@example.com");
        responseDTO.setBio("Hello World!");
        responseDTO.setSignupDate(new Date().toString());

        Mockito.when(userService.getUserById("user123")).thenReturn(responseDTO);

        mockMvc.perform(get("/v1/users/user123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user123"))
                .andExpect(jsonPath("$.nickname").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // 회원 목록 조회 테스트
    @Test
    void listUsersTest() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setNo(1L);
        responseDTO.setId("user123");
        responseDTO.setNickname("john_doe");
        responseDTO.setEmail("john@example.com");
        responseDTO.setBio("Hello World!");
        responseDTO.setSignupDate(new Date().toString());

        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(responseDTO));

        mockMvc.perform(get("/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("user123"))
                .andExpect(jsonPath("$[0].nickname").value("john_doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    // 회원 삭제 테스트
    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userService).deleteUser("user123");

        mockMvc.perform(delete("/v1/users/user123"))
                .andExpect(status().isNoContent());

        // Auto-Increment 초기화
        resetAutoIncrement();
    }

    // Auto-Increment 값 초기화
    private void resetAutoIncrement() throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement statement = connection.createStatement()) {

            // PostgreSQL 시퀀스 초기화
            statement.execute("ALTER SEQUENCE users_no_seq1 RESTART WITH 1");
        }
    }
}
