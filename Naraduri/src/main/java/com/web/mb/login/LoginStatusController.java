package com.web.mb.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginStatusController {

    @GetMapping("/check-login-status")
    public ResponseEntity<Map<String, Boolean>> checkLoginStatus(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        Boolean isLoggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
        response.put("isLoggedIn", isLoggedIn);
        return ResponseEntity.ok(response);
    }
}