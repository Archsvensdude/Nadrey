package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(nullable = false)
    private String site;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date register;

    @Column(nullable = false)
    private int hits;

    private Date up_date;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private int likes;
}