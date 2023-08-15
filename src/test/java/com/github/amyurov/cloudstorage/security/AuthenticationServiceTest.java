package com.github.amyurov.cloudstorage.security;

import com.github.amyurov.cloudstorage.dto.JwtResponse;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import com.github.amyurov.cloudstorage.service.AuthenticationService;
import com.github.amyurov.cloudstorage.util.JwtUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private AuthenticationService authenticationService;
    @Test
    void authenticate_shouldReturnJwt() {
        final UserCredentialsDTO credentials = mock(UserCredentialsDTO.class);
        when(credentials.getLogin()).thenReturn("login");
        when(credentials.getPassword()).thenReturn("password");
        var userToken = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
        when(authenticationManager.authenticate(userToken)).thenReturn(userToken);
        when(jwtUtil.generateJwt(userToken)).thenReturn("tokenToTest");
        final JwtResponse response = mock(JwtResponse.class);
        when(response.getToken()).thenReturn("tokenToTest");

        ResponseEntity<JwtResponse> HttpResponseActual = authenticationService.authenticate(credentials);
        String actual = HttpResponseActual.getBody().getToken();

        verify(authenticationManager).authenticate(userToken);
        verify(jwtUtil).generateJwt(userToken);
        assertEquals(response.getToken(), actual);

    }
}