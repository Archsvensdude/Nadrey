package com.web.mb;

import com.web.mb.AttractionSearching.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CommonController {

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInUser") != null; // 로그인 상태 여부 확인
    }

    @ModelAttribute("username")
    public String getUsername(HttpSession session) {
        Users user = (Users) session.getAttribute("loggedInUser");
        return (user != null) ? user.getId() : null; // 로그인된 사용자 ID 반환
    }
}
