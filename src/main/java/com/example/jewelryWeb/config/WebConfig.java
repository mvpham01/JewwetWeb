package com.example.jewelryWeb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // This applies CORS for all endpoints
                .allowedOrigins("http://localhost:3000")  // The frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed methods
                .allowedHeaders("*")  // Allows all headers
                .allowCredentials(true);  // Allow credentials like cookies if needed
    }
}