package com.web.mb.AttractionSearching.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class BoardLikes {

    @Id
    @Column(name = "ID", nullable = false)
    private int no; // 관광지 번호

    @Column(name = "USERS_ID", nullable = false)
    private String userId; // 사용자 아이디

    @Column(name = "BOARD_NO", nullable = false)
    private String boardId; // 사용자 아이디

}
