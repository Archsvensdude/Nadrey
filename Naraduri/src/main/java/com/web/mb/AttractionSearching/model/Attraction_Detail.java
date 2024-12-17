package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ATTRACTION_READ")
public class Attraction_Detail {

    @Column(nullable = false)
    private Long no;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String site;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String municipality;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String picture1;

    @Column(nullable = false)
    private String picture2;

    @Column(nullable = false)
    private String picture3;

    @Column(nullable = false)
    private String picture4;

    @Column(nullable = false)
    private String region;

}

