package com.github.amyurov.cloudstorage.registration;

import com.github.amyurov.cloudstorage.controller.RegistrationController;
import com.github.amyurov.cloudstorage.mapper.UserMapper;
import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import com.github.amyurov.cloudstorage.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    void registration_shouldCallService() {
        final UserCredentialsDTO dto = mock(UserCredentialsDTO.class);
        final User user = mock(User.class);
        when(userMapper.dtoToEntity(dto)).thenReturn(user);

        registrationController.registration(dto);

        verify(registrationService).saveUser(user);
    }
}