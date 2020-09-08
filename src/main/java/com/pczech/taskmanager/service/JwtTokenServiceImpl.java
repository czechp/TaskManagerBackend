package com.pczech.taskmanager.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service()
public class JwtTokenServiceImpl implements JwtTokenService {
    //todo: Implement it as input program paramenetr
    private final String KEY = "123PCZECH123";
    private final int EXPIRATION_MINUTES = 10;

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRATION_MINUTES))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }
}
