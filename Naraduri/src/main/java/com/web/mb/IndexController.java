package com.web.mb;

import com.web.mb.AiSearching.service.UserLikeToolsService;
import com.web.mb.AttractionSearching.model.UserLikeTools;
import com.web.mb.AttractionSearching.model.Users;
import com.web.mb.login.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    private final UserPageService userPageService;
    private final UserRepository userRepository;

    // 생성자에서 모든 의존성을 주입
    public IndexController(UserPageService userPageService, UserRepository userRepository) {
        this.userPageService = userPageService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String index(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("loggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null && isLoggedIn);
        return "th/index";
    }

    @Autowired
    private UserLikeToolsService userLikeToolsService;

    @GetMapping("/myPage/{id}")
    public String getUserPage(@PathVariable String id, HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser != null && loggedInUser.getId().equals(id)) {
            // 최신 데이터를 DB에서 다시 조회
            Users updatedUser = userRepository.findById(id).orElse(loggedInUser);
            model.addAttribute("users", updatedUser);

            // 좋아요 기록 조회
            List<UserLikeTools> likedSites = userLikeToolsService.getUserLikedSites(id);
            List<String> siteNames = likedSites.stream()
                    .map(UserLikeTools::getUserSite)
                    .collect(Collectors.toList());

            model.addAttribute("likedSites", siteNames);

            return "th/myPage";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/myPage")
    public String redirectToMyPage(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            return "redirect:/myPage/" + loggedInUser.getId();
        }

        return "redirect:/";
    }

    @GetMapping("/myPage/{id}/likes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserLikes(@PathVariable String id) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users loggedInUser = user.get();
            int totalLikes = loggedInUser.getUserLikes();

            return ResponseEntity.ok(Map.of(
                    "totalLikes", totalLikes
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "사용자를 찾을 수 없습니다."));
    }

    @GetMapping("/myPage/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getLikedSites(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        try {
            List<UserLikeTools> likedSites = userLikeToolsService.getUserLikedSites(loggedInUser.getId());
            List<String> siteNames = likedSites.stream()
                    .map(UserLikeTools::getUserSite)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "likedSites", siteNames,
                    "totalLikes", likedSites.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류가 발생했습니다."));
        }
    }
}