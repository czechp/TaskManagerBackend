package com.pczech.taskmanager.service;

import java.util.Map;

public interface JwtTokenService {
    String generateToken(String username);

    Map<String, Object> getDataFromToken(String authorizationHeader);
}
