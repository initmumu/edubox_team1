package com.gamja.edubox_team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamja.edubox_team1.user.model.dto.UserResponseDTO;
import com.gamja.edubox_team1.user.model.dto.UserSignupRequestDTO;
import com.gamja.edubox_team1.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // 회원가입 테스트
    @Test
    void createUserTest() throws Exception {
        UserSignupRequestDTO requestDTO = new UserSignupRequestDTO();
        requestDTO.setEmail("john@example.com");
        requestDTO.setPassword("securePassword123!");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEmail("john@example.com");
        responseDTO.setSignupDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Mockito.when(userService.createUser(any(UserSignupRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // 특정 회원 정보 조회 테스트
    @Test
    void getUserTest() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEmail("john@example.com");
        responseDTO.setNickname("john_doe");
        responseDTO.setBio("Hello World!");
        responseDTO.setSignupDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Mockito.when(userService.getUserById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
