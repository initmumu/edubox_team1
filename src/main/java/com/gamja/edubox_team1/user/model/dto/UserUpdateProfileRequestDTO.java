package com.gamja.edubox_team1.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 프로필 수정 요청 객체")
public class UserUpdateProfileRequestDTO {

    @Schema(description = "수정할 프로필 이미지 URL", example = "https://example.com/new_profile.jpg")
    private String img;

    @Schema(description = "수정할 닉네임", example = "new_nickname")
    private String nickname;

    @Schema(description = "수정할 사용자 소개 또는 바이오", example = "안녕하세요, 저는 새로운 개발자입니다.")
    private String bio;

    @Schema(description = "수정할 사용자 프로필 링크", example = "edubox.com/users/@new_profile")
    private String profileLink;
}
