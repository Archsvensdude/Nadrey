package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_LIKE_TOOLS")
public class UserLikeTools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long no;

    @Column(name = "USER_ID", nullable = false)
    private String userId; // 사용자 ID

    @Column(name = "USER_SITE", nullable = false)
    private String userSite; // 관광지 이름

    @Column(name = "USER_LIKES", nullable = false, columnDefinition = "int default 0")
    private int userLikes;

    // 기본 생성자
    public UserLikeTools() {}

    // Getter와 Setter
    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSite() {
        return userSite;
    }

    public void setUserSite(String userSite) {
        this.userSite = userSite;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }
}