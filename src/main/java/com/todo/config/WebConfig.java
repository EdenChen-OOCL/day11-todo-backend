package com.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 当前跨域请求最大有效时长。这里默认1小时
    private static final long MAX_AGE = 60 * 60;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*") // 允许请求携带的Header
                .exposedHeaders("Authorization") // 允许前端获取该响应头
                .allowCredentials(true) // 允许发送cookie
                .maxAge(MAX_AGE); // options请求的有效期
    }
}
