package com.pczech.taskmanager.configuration;

import com.pczech.taskmanager.interceptor.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration()
public class HttpInterceptorConfiguration implements WebMvcConfigurer {

    private HttpInterceptor httpInterceptor;

    @Autowired()
    public HttpInterceptorConfiguration(HttpInterceptor httpInterceptor) {
        this.httpInterceptor = httpInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor);
    }
}
