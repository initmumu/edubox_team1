package com.gamja.edubox_team1.auth.controller;

import com.gamja.edubox_team1.auth.dto.domain.TokenDto;
import com.gamja.edubox_team1.auth.dto.request.LoginRequestDto;
import com.gamja.edubox_team1.auth.dto.request.RefreshTokenRequestDto;
import com.gamja.edubox_team1.auth.dto.response.LoginResponseDto;
import com.gamja.edubox_team1.auth.dto.response.RefreshTokenResponseDto;
import com.gamja.edubox_team1.auth.exception.ExpiredAccessTokenException;
import com.gamja.edubox_team1.auth.exception.ExpiredRefreshTokenException;
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
            TokenDto tokens = authService.login(requestDto.getEmail(), requestDto.getPassword());
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer "+tokens.getAccessToken())
                    .body(CommonResponse.success(new LoginResponseDto(tokens.getRefreshToken())));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(CommonResponse.error(4012, e.getMessage()));
        } catch (ExpiredAccessTokenException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(CommonResponse.error(e.getCode(), e.getMessage()));
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<CommonResponse<RefreshTokenResponseDto>> reissue(@RequestBody RefreshTokenRequestDto requestDto) {
        try {
            TokenDto tokens = authService.refresh(requestDto.getRefreshToken());
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer "+tokens.getAccessToken())
                    .body(CommonResponse.success(null));
        } catch (ExpiredRefreshTokenException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(CommonResponse.error(e.getCode(), e.getMessage()));
        }
    }
}
