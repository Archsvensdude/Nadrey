package com.web.mb.AiSearching.service;

import com.web.mb.AttractionSearching.model.Board;
import com.web.mb.AttractionSearching.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRecommendationService {

    @Autowired
    private BoardRepository boardRepository;

    // 로그인된 사용자를 위한 개인화된 추천 로직
    public List<Board> getRecommendationsForUser(String userId) {
        // 사용자 기반 추천 로직 구현
        return boardRepository.findPersonalizedRecommendations(userId);
    }

    // 비로그인 사용자를 위한 기본 추천 로직
    public List<Board> getDefaultRecommendations() {
        // 인기 관광지 또는 랜덤 관광지를 반환
        return boardRepository.findTop5ByOrderByLikesDesc();
    }
}

