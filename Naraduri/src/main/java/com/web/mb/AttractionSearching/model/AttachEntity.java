package com.web.mb.AttractionSearching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "FILE_ATTACHMENT")
public class AttachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String original_FileName; // 원본 파일 이름

    @Column(nullable = false)
    private String stored_FileName; // 서버에 저장된 파일 이름

    @Column(nullable = false)
    private String filePath; // 파일이 저장된 경로

    @Column(nullable = false)
    private Long fileSize; // 파일 크기 (바이트 단위)

    @Column(nullable = false)
    private LocalDateTime up_date; // 파일 업로드 날짜 및 시간

    /* // 필요한 경우, 파일을 연관된 엔티티(예: AttractionEntity)와 매핑할 수 있습니다.
    // 예시로 @ManyToOne 관계를 추가
    @ManyToOne
    @JoinColumn(name = "attraction_id", referencedColumnName = "no", nullable = false)
    private Attraction_Detail attractionDetail; */

}