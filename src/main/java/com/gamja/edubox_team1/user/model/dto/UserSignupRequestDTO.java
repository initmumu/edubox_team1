package com.gamja.edubox_team1.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 회원가입 요청 객체")
public class UserSignupRequestDTO {

    @Schema(description = "사용자 이메일", example = "johnDoe1122@gmail.com")
    private String email;

    @Schema(description = "사용자 비밀번호(평문)", example = "P@ssw0rd123")
    private String password;
}
