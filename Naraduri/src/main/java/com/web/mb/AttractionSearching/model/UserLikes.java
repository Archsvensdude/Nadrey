package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_LIKES")
public class UserLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String siteName;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime likedDate;

    public void setUserId(String userId) {
    }

    public void setSiteName(String siteName) {
    }

    public void setLikedDate(LocalDateTime now) {
    }

    // Getters, Setters, Constructors
}
