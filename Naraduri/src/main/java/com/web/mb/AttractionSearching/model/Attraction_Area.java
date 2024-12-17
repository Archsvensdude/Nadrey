package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ATTRACTION_AREA")
public class Attraction_Area {

    @Column(nullable = false)
    private Long no;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String site;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String municipality;

    @Column(nullable = false)
    private String picture1;

    @Column(nullable = false)
    private String picture2;

    @Column(nullable = false)
    private String picture3;

    @Column(nullable = false)
    private String picture4;

}
