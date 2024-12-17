package com.web.mb.AttractionSearching.service;

import com.web.mb.AttractionSearching.model.Attraction_Detail;
import com.web.mb.AttractionSearching.repository.AttractionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AttractionDetailService {

    @Autowired
    private AttractionDetailRepository attractionDetailRepository;

    // site를 통해 관광지 정보 검색
    public Optional<Attraction_Detail> getAttractionsBySite(String site) {
        return attractionDetailRepository.findBySite(site);
    }

    // no를 통해 특정 관광지 정보 검색
    public Optional<Attraction_Detail> getAttractionsByNo(Long no) {
        return attractionDetailRepository.findByNo(no);
    }

    // 새로운 관광지 추가 및 기존 관광지 업데이트
    public void saveAttraction(Attraction_Detail attractionDetail) {
        attractionDetailRepository.save(attractionDetail);
    }

    @Transactional // 트랜잭션 시작
    public void deleteAttraction(Attraction_Detail attractionDetail) {
        attractionDetailRepository.delete(attractionDetail);
    }
}