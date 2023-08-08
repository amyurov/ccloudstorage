package com.github.amyurov.cloudstorage.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    // Секрет для подписи токена
    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.lifetime:60}")
    private long jwtLifetime;

    // Метод генерации токена
    public String generateJwt (Authentication auth) {
        // Получим данные, которые будем передавать в claims токена
        String username = auth.getName();
        Date expiredDate = Date.from(ZonedDateTime.now().plusMinutes(jwtLifetime).toInstant());

        log.info("Сгенерирован токен для пользователя " + username);

        return JWT.create()
                .withIssuer("Netology Cloud Storage")
                .withIssuedAt(new Date())
                .withExpiresAt(expiredDate)
                .withSubject("User details")
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(secret));
    }

    // Валидация токена, при успехе возвращаются claims
    private Map<String, Claim> getClaims(String token) throws JWTVerificationException {
        DecodedJWT jwt = decodedJWT(token);
        return jwt.getClaims();
    }

    private DecodedJWT decodedJWT(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Netology Cloud Storage")
                .build();

        return jwtVerifier.verify(token);
    }

    public String getName(String token) {
        return getClaims(token).get("username").asString();
    }

    public JwtEntity stringToJwtEntity(String token) {
        Date expiresAt = decodedJWT(token).getExpiresAt();
        return new JwtEntity(token, expiresAt);
    }
}
