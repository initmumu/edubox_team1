package com.gamja.edubox_team1.auth.controller;

import com.gamja.edubox_team1.auth.dto.request.LoginRequestDto;
import com.gamja.edubox_team1.auth.dto.response.LoginResponseDto;
import com.gamja.edubox_team1.auth.service.AuthService;
import com.gamja.edubox_team1.common.dto.response.CommonResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        try {
            String token = authService.login(requestDto.getUsername(), requestDto.getPassword());
            return ResponseEntity.ok(CommonResponse.success(new LoginResponseDto(token)));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(CommonResponse.error(4012, "INVALID_USER_CREDENTIALS"));
        }
    }
}
