package com.pczech.taskmanager.service;

import java.util.Date;
import java.util.Map;

public interface JwtTokenService {
    String generateToken(String username);

    Map<String, Object> getDataFromToken(String authorizationHeader);

    boolean tokenNotExpired(Date expiration);
}
