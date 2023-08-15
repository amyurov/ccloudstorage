package com.github.amyurov.cloudstorage.security;

import com.github.amyurov.cloudstorage.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final JwtService jwtService;
    @Value("${app.jwt.token_header:auth-token}")
    private String tokenHeader;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // При логауте нужно отозвать выданный токен и поместить его в блэк лист
        String token = request.getHeader(tokenHeader).substring(7);
        jwtService.retrieveToken(token);
    }
}
