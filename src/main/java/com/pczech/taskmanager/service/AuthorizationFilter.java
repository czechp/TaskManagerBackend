package com.pczech.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component()
public class AuthorizationFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private JwtTokenService jwtTokenService;

    @Autowired()
    public AuthorizationFilter(UserDetailsService userDetailsService, JwtTokenService jwtTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        Map<String, Object> dataFromToken = jwtTokenService.getDataFromToken(authorization);
        UserDetails user = null;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if (!dataFromToken.isEmpty()) {
            try {
                user = userDetailsService.loadUserByUsername((String) dataFromToken.get("username"));
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            } catch (Exception e) { }
        }

        if(user != null && usernamePasswordAuthenticationToken != null){
            //todo: inject to SecurityContextHolder
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
