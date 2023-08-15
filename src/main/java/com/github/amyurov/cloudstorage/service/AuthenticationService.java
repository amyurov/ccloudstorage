package com.github.amyurov.cloudstorage.service;

import com.github.amyurov.cloudstorage.dto.JwtResponse;
import com.github.amyurov.cloudstorage.util.JwtUtil;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseEntity<JwtResponse> authenticate(UserCredentialsDTO user) {
        var authToken = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

        Authentication authentication;
        authentication = authenticationManager.authenticate(authToken);

        // Если аутентификация пройдена генерируем и возвращаем токен
        String jwt = jwtUtil.generateJwt(authentication);
        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
    }
}
