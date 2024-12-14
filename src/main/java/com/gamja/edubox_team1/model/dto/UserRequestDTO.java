package com.gamja.edubox_team1.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 생성 요청 객체")
public class UserRequestDTO {

    //todo: 아이디, 닉네임, 한 줄 소개 길이 조정 필요
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Size(min = 7, max = 20, message = "아이디는 7자 이상, 20자 이하여야 합니다.")
    @Schema(description = "사용자 아이디", example = "user123")
    private String id;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 7, max = 20, message = "비밀번호는 7자 이상, 20자 이하여야 합니다.")
    @Schema(description = "사용자 비밀번호", example = "pwpw135")
    private String password;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(max = 20, message = "닉네임은 최대 20자까지 가능합니다.")
    @Schema(description = "사용자 닉네임", example = "john_doe")
    private String nickname;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @Schema(description = "사용자 이메일", example = "johnDoe1122@gmail.com")
    private String email;

    @Schema(description = "사용자 소개 또는 바이오", example = "안녕하세요, 저는 개발자입니다.")
    private String bio;
}
