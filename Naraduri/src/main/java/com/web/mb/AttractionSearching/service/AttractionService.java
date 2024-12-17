package com.web.mb.AttractionSearching.service;

import com.web.mb.AttractionSearching.model.Attraction_Detail;
import com.web.mb.AttractionSearching.model.Board;
import com.web.mb.AttractionSearching.repository.AttractionDetailRepository;
import com.web.mb.AttractionSearching.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private AttractionDetailRepository attractionDetailRepository;

    /* 관광지 전체 리스트 조회
    public List<Board> getAllAttractions() {
        return attractionRepository.findAll();
    } */

    // 특정 관광지 정보 조회
    public Optional<Board> getAttractionByNo(Long no) {
        return attractionRepository.findById(no);
    }

    // 관광지 정보 삭제
    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }

    // 기본 페이지네이션 메소드
    public Page<Board> getAttractionsPage(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("no").ascending());
        return attractionRepository.findAll(pageable);
    }

    // 검색 조건을 반영한 페이지네이션 메소드
    public Page<Board> searchAttractionsPage(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("no").ascending());
        return attractionRepository.findBySiteContaining(keyword, pageable);
    }

    // 새로운 메소드: 검색어 기반 데이터 조회
    public Page<Board> searchAttractions(String keyword, int page) {
        return attractionRepository.findBySiteContainingIgnoreCase(keyword, PageRequest.of(page, 10));
    }

}