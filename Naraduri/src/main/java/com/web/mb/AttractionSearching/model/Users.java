package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private String id; // 사용자 아이디

    @Column(name = "PWD", nullable = false)
    private String password; // 비밀번호

    @Column(name = "NAME", nullable = false)
    private String name; // 사용자 이름

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email; // 이메일

    @Column(name = "BIRTH")
    private String birth; // 생년월일 (문자열로 저장, 형식: yy/MM/dd)

    @Column(name = "REGISTER")
    private LocalDate registerDate; // 가입일 (LocalDate로 저장)

    @Column(name = "USER_LIKES", nullable = false)
    private int userLikes; // 좋아요 수

    // 추가 생성자
    public Users(String id, String password, String name, String email, String birth, LocalDate registerDate, int userLikes) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.registerDate = registerDate;
        this.userLikes = userLikes;
    }
}