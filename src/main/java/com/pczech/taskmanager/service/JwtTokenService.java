package com.pczech.taskmanager.service;

public interface JwtTokenService {
    String generateToken(String username);
}
