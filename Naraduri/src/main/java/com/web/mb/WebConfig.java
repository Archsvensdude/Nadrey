package com.web.mb;

import com.web.mb.login.LoginStatusInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginStatusInterceptor loginStatusInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginStatusInterceptor)
                .addPathPatterns("/**")  // 모든 경로에 Interceptor 적용
                .excludePathPatterns("/static/**");  // 정적 리소스 제외
    }
}
