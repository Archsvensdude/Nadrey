package com.web.mb.login;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginStatusInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 요청을 처리한 후에 로그인 상태를 Model에 추가
        if (modelAndView != null && handler instanceof HandlerMethod) {
            HttpSession session = request.getSession();
            Boolean isLoggedIn = (Boolean) session.getAttribute("loggedIn");
            modelAndView.addObject("isLoggedIn", isLoggedIn != null && isLoggedIn);
        }
    }
}
