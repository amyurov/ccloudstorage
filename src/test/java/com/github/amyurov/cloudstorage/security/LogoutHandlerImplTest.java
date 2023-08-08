package com.github.amyurov.cloudstorage.security;

import com.github.amyurov.cloudstorage.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class LogoutHandlerImplTest {
    private final static String TOKEN = "token";

    @Value("${app.jwt.token_header:auth-token}")
    private String tokenHeader;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private LogoutHandlerImpl logoutHandler;

    @Test
    void logout_shouldCallJwtService() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);
        when(request.getHeader(tokenHeader)).thenReturn(("Bearer " + TOKEN));

        logoutHandler.logout(request, response, authentication);

        verify(jwtService).retrieveToken(TOKEN);
    }
}