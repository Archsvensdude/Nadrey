package com.web.mb.login;

import com.web.mb.AttractionSearching.model.Users;
import com.web.mb.login.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/login")
@SessionAttributes("loggedIn")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String loginForm(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("loggedInUser");

        model.addAttribute("usersId", user != null ? user.getId() : "");
        model.addAttribute("usersPassword", user != null ? user.getPassword() : "");

        if (user != null) {
            model.addAttribute("users", user);
            System.out.println("Logged in user: " + user.getId());
        } else {
            model.addAttribute("users", new Users());
            System.out.println("No logged in user found. Using default Users object.");
        }

        return "th/loginForm";
    }

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Users> userOptional = userRepository.findByIdAndPassword(username, password);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", user);

            return ResponseEntity.ok(Map.of("success", true, "username", user.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "아이디 또는 비밀번호가 잘못되었습니다."));
        }
    }

    @GetMapping("/session")
    @ResponseBody
    public Map<String, String> getSessionData(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser"); // 세션에서 사용자 객체 가져오기
        Map<String, String> response = new HashMap<>();

        if (loggedInUser != null) {
            response.put("username", loggedInUser.getId());  // 사용자 아이디
            response.put("name", loggedInUser.getName());    // 사용자 이름
        } else {
            response.put("username", null);
            response.put("name", null);
        }

        return response;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화로 로그아웃 처리
        return "redirect:/"; // 로그아웃 후 메인 페이지로 리다이렉트
    }

    // 로그인 상태를 확인하는 API
    @GetMapping("/status")
    public ResponseEntity<?> checkLoginStatus(HttpSession session) {
        Users user = (Users) session.getAttribute("loggedInUser");
        if (user != null) {
            return ResponseEntity.ok(Map.of("isLoggedIn", true, "username", user.getId(), "name", user.getName()));
        } else {
            return ResponseEntity.ok(Map.of("isLoggedIn", false));
        }
    }

    // 로그아웃을 처리하는 API
    @PostMapping("/api/logout")
    @ResponseBody
    public Map<String, String> logoutApi(HttpSession session) {
        session.invalidate(); // 세션 무효화
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃되었습니다.");
        return response;
    }

    @GetMapping("/signUp")
    public String userSignUp() {
        return "th/signUp";
    }

    @PostMapping("/signUp")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birth") String birth,
            @RequestParam(value = "userlikes", required = false, defaultValue = "0") int userlikes, // 기본값 설정
            Model model) {

        // 아이디 중복 검사
        if (userRepository.existsById(username)) {
            model.addAttribute("error1", "이미 사용 중인 아이디입니다.");
            return "th/signUp";
        }

        // 이메일 중복 검사
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error2", "이미 사용 중인 이메일입니다.");
            return "th/signUp";
        }

        // 사용자 정보 저장
        Users newUser = new Users(username, password, name, email, birth, LocalDate.now(), userlikes);
        userRepository.save(newUser);

        // 회원가입 완료 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }

    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam("username") String username) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", !userRepository.existsById(username));
        return response;
    }

    @GetMapping("/check-email")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", !userRepository.existsByEmail(email));
        return response;
    }

    @GetMapping("/findById")
    public String findIdPage() {
        return "th/findById";
    }

    @PostMapping("/findById")
    public String findId(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model) {

        // 데이터베이스에서 해당 이름과 이메일로 아이디 조회
        Optional<Users> userOptional = userRepository.findByNameAndEmail(name, email);

        if (userOptional.isPresent()) {
            // 아이디 조회 후 마스킹 처리
            String originalId = userOptional.get().getId();
            String maskedId = maskId(originalId);
            model.addAttribute("foundId", maskedId); // 마스킹된 아이디 전달
        } else {
            // 조회되지 않은 경우 에러 메시지 출력
            model.addAttribute("error", "해당 정보로 아이디를 찾을 수 없습니다.");
        }

        return "th/findById"; // 결과를 포함한 페이지로 다시 이동
    }

    // 아이디 마스킹 메서드
    private String maskId(String id) {
        if (id.length() <= 4) {
            return "*".repeat(id.length()); // 아이디 길이가 4 이하일 경우 전체 마스킹
        }
        String visiblePart = id.substring(0, 4); // 처음 4자리 보이기
        String maskedPart = "*".repeat(id.length() - 4); // 나머지 부분 마스킹
        return visiblePart + maskedPart;
    }

    @GetMapping("/findByPwd")
    public String findPwdPage() {
        return "th/findByPwd";
    }

    @PostMapping("/findByPwd")
    public String findPassword(
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            Model model) {

        // 데이터베이스에서 해당 이름, 아이디, 이메일로 비밀번호 조회
        Optional<Users> user = userRepository.findByNameAndIdAndEmail(name, username, email);

        if (user.isPresent()) {
            // 조회된 비밀번호를 모델에 추가하여 화면에 출력
            String originalPassword = user.get().getPassword();
            String maskedPassword = maskPassword(originalPassword);
            model.addAttribute("foundPassword", maskedPassword); // 마스킹된 아이디 전달
        } else {
            // 조회되지 않은 경우 에러 메시지 출력
            model.addAttribute("error", "해당 정보로 비밀번호를 찾을 수 없습니다.");
        }

        return "th/findByPwd"; // 결과를 포함한 페이지로 다시 이동
    }

    // 비밀번호 마스킹 메서드
    private String maskPassword(String password) {
        if (password.length() <= 6) {
            return "*".repeat(password.length()); // 아이디 길이가 6 이하일 경우 전체 마스킹
        }
        String visiblePart = password.substring(0, 6); // 처음 6자리 보이기
        String maskedPart = "*".repeat(password.length() - 6); // 나머지 부분 마스킹
        return visiblePart + maskedPart;
    }

    @GetMapping("/update")
    public String UpdateInformation() {
        return "th/update";
    }

    @PostMapping("/update")
    public String updateUserInfo(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("name") String name,
            @RequestParam("birth") String birth,
            HttpSession session,
            Model model) {

        // 세션에서 로그인된 사용자 가져오기
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            model.addAttribute("error", "로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        // 비밀번호 확인
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error1", "비밀번호가 일치하지 않습니다.");
            return "th/update";
        }

        // 사용자 정보 업데이트
        loggedInUser.setPassword(password); // 비밀번호 변경
        loggedInUser.setName(name); // 이름 변경
        loggedInUser.setBirth(birth); // 생년월일 변경
        userRepository.save(loggedInUser); // 저장

        // 세션에 업데이트된 사용자 정보 반영
        session.setAttribute("loggedInUser", loggedInUser);

        return "redirect:/myPage/" + loggedInUser.getId();
    }
}

