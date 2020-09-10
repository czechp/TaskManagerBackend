package com.pczech.taskmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service()
@Slf4j()
public class JwtTokenServiceImpl implements JwtTokenService {
    //todo: Implement it as input program paramenetr
    private final String KEY = "123PCZECH123";
    private final int EXPIRATION_MINUTES = 60;

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRATION_MINUTES))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    @Override
    public Map<String, Object> getDataFromToken(String authorizationHeader) {
        Map<String, Object> result = new HashMap<>();
        try{
            String token = authorizationHeader.substring(7);
            Claims body = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();
            result.put("username", body.getSubject());
            result.put("expiration", body.getExpiration());
        }catch (Exception e){
            log.info("Authorization with incorrect token");
        }

        return result;
    }

    @Override
    public boolean tokenNotExpired(Date expiration) {
        return new Date(System.currentTimeMillis()).before(expiration);
    }
}
