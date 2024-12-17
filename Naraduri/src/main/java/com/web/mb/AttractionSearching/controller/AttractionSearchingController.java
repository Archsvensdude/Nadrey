package com.web.mb.AttractionSearching.controller;

import com.web.mb.AiSearching.service.UserLikeToolsService;
import com.web.mb.AttractionSearching.model.*;
import com.web.mb.AttractionSearching.service.*;
import com.web.mb.login.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/attraction")
public class AttractionSearchingController {

    @Autowired
    private AttractionService attractionService;

    // 관광지 안내 게시판 (페이징 처리된 관광지 목록 보기)
    @GetMapping("")
    public String getAttractions(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(required = false) String search,
                                 Model model) {
        int actualPage = page - 1;

        Page<Board> attractionPage = (search == null || search.isEmpty())
                ? attractionService.getAttractionsPage(actualPage)
                : attractionService.searchAttractionsPage(search, actualPage);

        model.addAttribute("board", attractionPage.getContent());
        model.addAttribute("currentPage", actualPage);
        model.addAttribute("totalPages", attractionPage.getTotalPages());
        model.addAttribute("search", search);

        return "th/attractionBasic";
    }

    @Autowired
    private AttractionDetailService attractionDetailService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/site")
    public String redirectToAttraction() {
        return "redirect:/attraction"; // /attraction으로 리다이렉트
    }

    @GetMapping("/site/")
    public String redirectToAttraction2() {
        return "redirect:/attraction"; // /attraction으로 리다이렉트
    }

    @GetMapping("/site/{site}")
    public String viewAttraction(
            @PathVariable String site,
            @RequestParam(defaultValue = "1") int page, // 페이지 번호 추가
            Model model) {

        // Optional 형태로 Attraction_Detail 가져오기
        Optional<Attraction_Detail> attractionDetail = attractionDetailService.getAttractionsBySite(site);

        if (attractionDetail.isPresent()) {
            // Attraction_Detail이 존재하면 데이터를 Model에 추가
            model.addAttribute("attraction", attractionDetail.get()); // 상세 정보 전달
            model.addAttribute("currentPage", page); // 현재 페이지 번호 추가
            return "th/attractionDetail";
        } else {
            // Attraction_Detail이 없으면 목록 페이지로 리다이렉트
            return "redirect:/attraction?page=" + page;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/site/{site}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeAttractionGet(@PathVariable String site, HttpSession session) {
        return likeAttraction(site, session); // 동일 로직 사용
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserLikeToolsService userLikeToolsService;

    @PostMapping("/site/{site}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeAttraction(@PathVariable String site, HttpSession session) {
        try {
            // 세션에서 사용자 정보 가져오기
            Users loggedInUser = (Users) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "로그인이 필요합니다."
                ));
            }

            // 관광지(Board) 데이터 가져오기
            Board board = boardService.findFirstBySite(site);
            if (board == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "message", "해당 관광지를 찾을 수 없습니다."
                ));
            }

            // 좋아요 수 업데이트
            board.setLikes(board.getLikes() + 1);
            boardService.saveBoard(board);

            // 사용자 누적 좋아요 수 증가 (SQL 직접 실행)
            String updateUserLikesSQL = "UPDATE USERS SET USER_LIKES = USER_LIKES + 1 WHERE ID = ?";
            jdbcTemplate.update(updateUserLikesSQL, loggedInUser.getId());

            // USER_LIKE_TOOLS 테이블 업데이트
            userLikeToolsService.addUserLike(loggedInUser.getId(), site);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "좋아요가 반영되었습니다!",
                    "likes", board.getLikes()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 내부 오류: " + e.getMessage()
            ));
        }
    }

    // 새로운 관광지 등록 폼으로 이동
    @GetMapping("/new")
    public String newAttractionForm(Model model) {
        model.addAttribute("attractionDetail", new Attraction_Detail());
        return "th/attractionForm";
    }

    // 새로운 관광지 등록 처리
    @PostMapping("/new")
    public String createAttraction(@ModelAttribute Attraction_Detail attractionDetail, RedirectAttributes redirectAttributes) {
        // 관광지 정보 저장
        attractionDetailService.saveAttraction(attractionDetail);

        // 새 관광지에 대한 게시글 추가
        Board boardEntry = new Board();
        boardEntry.setSite(attractionDetail.getSite());
        boardEntry.setContent(attractionDetail.getContent());
        boardEntry.setRegister(new Date());
        boardEntry.setHits(0);
        boardEntry.setPicture(attractionDetail.getPicture1());
        boardService.saveBoard(boardEntry);

        redirectAttributes.addFlashAttribute("message", "새로운 관광지가 성공적으로 등록되었습니다.");
        return "redirect:/attraction";
    }

    // 관광지 상세보기 및 관련 게시글 조회
    @GetMapping("/{site}")
    public String viewAttraction(@PathVariable String site, Model model) {
        Optional<Attraction_Detail> attractionDetail = attractionDetailService.getAttractionsBySite(site);
        if (attractionDetail.isPresent()) {
            model.addAttribute("attractionDetail", attractionDetail.get());

            // 해당 site와 관련된 게시글 목록을 조회하여 추가
            List<Board> boards = boardService.getBoardsBySite(site);
            model.addAttribute("boards", boards);

            return "th/attractionDetail";
        } else {
            return "redirect:/attraction";
        }
    }

    // 관광지 수정 페이지로 리다이렉트
    @GetMapping("/edit/no/{no}")
    public String redirectToAttractionSite(@PathVariable Long no, RedirectAttributes redirectAttributes) {
        Optional<Attraction_Detail> attraction = attractionDetailService.getAttractionsByNo(no);
        if (attraction.isPresent()) {
            redirectAttributes.addAttribute("site", attraction.get().getSite());
            return "redirect:/attraction/edit/{site}";
        } else {
            return "redirect:/attraction";
        }
    }

    // 관광지 수정 폼
    @GetMapping("/edit/{site}")
    public String editAttractionForm(@PathVariable String site, Model model) {
        Optional<Attraction_Detail> attractionDetail = attractionDetailService.getAttractionsBySite(site);
        if (attractionDetail.isPresent()) {
            model.addAttribute("attractionDetail", attractionDetail.get());
            return "th/attractionEdit";
        } else {
            return "redirect:/attraction";
        }
    }

    // 관광지 수정 처리
    @PostMapping("/edit/{site}")
    public String updateAttraction(@PathVariable String site, @ModelAttribute Attraction_Detail updatedAttraction) {
        Optional<Attraction_Detail> attraction = attractionDetailService.getAttractionsBySite(site);
        if (attraction.isPresent()) {
            Attraction_Detail existingAttraction = attraction.get();
            existingAttraction.setSite(updatedAttraction.getSite());
            existingAttraction.setTheme(updatedAttraction.getTheme());
            existingAttraction.setState(updatedAttraction.getState());
            existingAttraction.setMunicipality(updatedAttraction.getMunicipality());
            existingAttraction.setContent(updatedAttraction.getContent());
            existingAttraction.setPicture1(updatedAttraction.getPicture1());
            existingAttraction.setPicture2(updatedAttraction.getPicture2());
            existingAttraction.setPicture3(updatedAttraction.getPicture3());
            existingAttraction.setPicture4(updatedAttraction.getPicture4());
            existingAttraction.setRegion(updatedAttraction.getRegion());
            attractionDetailService.saveAttraction(existingAttraction); // 수정된 데이터 저장

            // 관련 게시글도 업데이트 (내용을 관광지 설명으로 덮어쓰기)
            List<Board> boards = boardService.getBoardsBySite(site);
            for (Board board : boards) {
                board.setContent(updatedAttraction.getContent());
                boardService.saveBoard(board);
            }
        }
        return "redirect:/attraction";
    }

    // 관광지 삭제 및 관련 게시글 삭제
    @GetMapping("/delete/{site}")
    public String deleteAttraction(@PathVariable String site) {
        // 관련 게시글 삭제
        boardService.deleteBoardsBySite(site);

        // 관광지 삭제
        Optional<Attraction_Detail> attraction = attractionDetailService.getAttractionsBySite(site);
        attraction.ifPresent(attractionDetailService::deleteAttraction);

        return "redirect:/attraction";
    }

    @Autowired
    private AttractionAreaService attractionAreaService;

    @GetMapping("/area") // 지역별 관광지 안내 관련 게시판
    public String areaAttraction(Model model, @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "ALL") String region) {
        int actualPage = page - 1;
        Page<Attraction_Area> attractions = attractionAreaService.getAttractionsByRegion(actualPage, region);

        model.addAttribute("attractions", attractions.getContent());
        model.addAttribute("currentPage", actualPage);
        model.addAttribute("totalPages", attractions.getTotalPages());
        model.addAttribute("selectedRegion", region);

        return "th/attractionByArea";
    }

    @Autowired
    private AttractionThemeService attractionThemeService;

    @GetMapping("/theme")
    public String attractionByTheme(Model model, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "ALL") String category) {
        int actualPage = page - 1;
        Page<Attraction_Theme> attractions = attractionThemeService.getAttractionsByTheme(actualPage, category);;

        model.addAttribute("attractions", attractions.getContent());
        model.addAttribute("currentPage", actualPage);
        model.addAttribute("totalPages", attractions.getTotalPages());
        model.addAttribute("selectedCategory", category);
        return "th/attractionByTheme";
    }

    @GetMapping("/map")
    public String AttractionMapAPI() {
        return "th/map";
    }

}
