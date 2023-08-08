package com.github.amyurov.cloudstorage.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtRepository jwtRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void retrieveToken(String token) {
        JwtEntity jwtEntity = null;
        try {
            jwtEntity = jwtUtil.stringToJwtEntity(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
        jwtRepository.save(jwtEntity);
//        jwtRepository.removeExpired();
    }

    public boolean isRetrieved(String token) {
        return jwtRepository.existsJwtEntityByToken(token);
    }
}
