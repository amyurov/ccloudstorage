package com.github.amyurov.cloudstorage.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String NAME = "name";
    private static final String PASS = "pass";

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void LoadUserByUsername_shouldReturnValidUserDetails() {
        User user = new User(NAME, PASS);
        user.setRole(UserRole.ROLE_USER);
        var role = new SimpleGrantedAuthority(user.getRole().toString());
        when(userRepository.findByName(NAME)).thenReturn(Optional.of(user));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(),
                Collections.singletonList(role));

        UserDetails actual = userService.loadUserByUsername(NAME);

        assertEquals(userDetails.getUsername(), actual.getUsername());
        assertEquals(userDetails.getPassword(), actual.getPassword());
        assertArrayEquals(actual.getAuthorities().toArray(), userDetails.getAuthorities().toArray());
    }

    @Test
    void findByName_shouldReturnUser() {
        final User user = mock(User.class);
        when(userRepository.findByName(NAME)).thenReturn(Optional.ofNullable(user));

        final User actual = userService.findByName(NAME);

        assertNotNull(actual);
        assertEquals(user, actual);
        verify(userRepository).findByName(NAME);
    }
}