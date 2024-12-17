package com.web.mb.AiSearching;

import com.web.mb.AttractionSearching.model.Board;
import com.web.mb.AiSearching.service.AiRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/AiSearching")
public class AiSearchingController {

    @Autowired
    private AiRecommendationService aiRecommendationService;

    @GetMapping("")
    public ModelAndView attractionAiSearch(HttpSession session, Model model) {
        // 세션에서 사용자 정보 확인
        String userId = (String) session.getAttribute("userId");

        List<Board> recommendedAttractions;
        if (userId != null) {
            // 로그인된 사용자는 개인화된 추천 데이터를 제공
            model.addAttribute("userId", userId);
            recommendedAttractions = aiRecommendationService.getRecommendationsForUser(userId);
        } else {
            // 비로그인 사용자는 기본 추천 데이터를 제공
            recommendedAttractions = aiRecommendationService.getDefaultRecommendations();
        }

        // 추천 데이터를 모델에 추가
        model.addAttribute("recommendations", recommendedAttractions);

        // AI 탐색 페이지로 이동
        return new ModelAndView("th/AiSearching");
    }
}