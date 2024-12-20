package com.gamja.edubox_team1.user.model.dto;
import com.gamja.edubox_team1.user.model.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 정보 응답 객체")
public class UserResponseDTO {

    @Schema(description = "사용자 고유 번호", example = "12345")
    private long id;

    @Schema(description = "사용자 이메일", example = "johnDoe1122@gmail.com")
    private String email;

    @Schema(description = "사용자 닉네임", example = "john_doe")
    private String nickname;

    @Schema(description = "사용자 권한 유형", allowableValues = {"STUDENT", "CREATOR", "ADMIN"}, example = "STUDENT")
    private UserRole role;

    @Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String img;

    @Schema(description = "사용자 소개 또는 바이오", example = "안녕하세요, 저는 개발자입니다.")
    private String bio;

    @Schema(description = "사용자 프로필 링크", example = "edubox.com/users/@cmh1199")
    private String profileLink;

    @Schema(description = "사용자 가입 날짜", example = "2024-01-01")
    private String signupDate;
}
