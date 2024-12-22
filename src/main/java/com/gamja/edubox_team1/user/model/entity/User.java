package com.gamja.edubox_team1.user.model.entity;

import com.gamja.edubox_team1.user.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // 데이터베이스의 "users" 테이블과 매핑
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키, 자동 증가

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // SHA-256 암호화된 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.STUDENT; // 사용자 권한 (Enum)

    @Column
    private String img; // 프로필 이미지 URL

    // todo: length 조절 필요
    @Column(length = 500)
    private String bio; // 사용자 소개

    @Column
    private String profileLink;

    @Column(nullable = false, updatable = false)
    private LocalDateTime signupDate; // 가입 날짜 (생성 시 고정)
}
