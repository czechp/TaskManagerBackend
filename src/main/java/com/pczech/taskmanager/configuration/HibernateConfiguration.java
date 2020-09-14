package com.pczech.taskmanager.configuration;

import com.pczech.taskmanager.interceptor.HibernateInterceptorComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration()
public class HibernateConfiguration implements HibernatePropertiesCustomizer {
    private final HibernateInterceptorComponent hibernateInterceptorComponent;

    @Autowired()
    public HibernateConfiguration(HibernateInterceptorComponent hibernateInterceptorComponent) {
        this.hibernateInterceptorComponent = hibernateInterceptorComponent;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", hibernateInterceptorComponent);
    }
}
