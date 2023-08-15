package com.github.amyurov.cloudstorage.security;

import com.github.amyurov.cloudstorage.controller.AuthenticationController;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import com.github.amyurov.cloudstorage.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void auth_shouldCallService() {
        final UserCredentialsDTO credential = mock(UserCredentialsDTO.class);

        authenticationController.auth(credential);

        verify(authenticationService).authenticate(credential);

    }
}