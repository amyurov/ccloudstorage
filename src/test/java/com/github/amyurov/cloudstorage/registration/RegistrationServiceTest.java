package com.github.amyurov.cloudstorage.registration;

import com.github.amyurov.cloudstorage.user.User;
import com.github.amyurov.cloudstorage.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void saveUser_shouldCallRepository() {
        final User user = mock(User.class);
        when(user.getName()).thenReturn("name");
        when(user.getPassword()).thenReturn("password");
        when(userRepository.existsUserByName("name")).thenReturn(false);
        when(encoder.encode(user.getPassword())).thenReturn("encodedPassword");

        registrationService.saveUser(user);

        verify(userRepository).save(user);
    }
}