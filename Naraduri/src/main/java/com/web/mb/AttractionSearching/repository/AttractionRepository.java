package com.web.mb.AttractionSearching.repository;

import com.web.mb.AttractionSearching.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Board, Long> {
    // 관광지 목록을 페이지 단위로 가져오는 메서드
    Page<Board> findAllByOrderByNoAsc(Pageable pageable);

    // 이름에 키워드가 포함된 데이터를 찾는 메소드
    Page<Board> findBySiteContaining(String keyword, Pageable pageable);

    Page<Board> findBySiteContainingIgnoreCase(String keyword, Pageable pageable);
}