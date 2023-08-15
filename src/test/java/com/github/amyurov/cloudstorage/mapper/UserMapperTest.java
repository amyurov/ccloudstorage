package com.github.amyurov.cloudstorage.mapper;

import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    @Test
    void dtoToEntity() {
        final UserCredentialsDTO dto = mock(UserCredentialsDTO.class);
        when(dto.getLogin()).thenReturn("login");
        when(dto.getPassword()).thenReturn("password");

        User actual = userMapper.dtoToEntity(dto);

        assertEquals(dto.getLogin(), actual.getName());
        assertEquals(dto.getPassword(), actual.getPassword());
    }

    @Test
    void entityToDto() {
        final User user = mock(User.class);
        when(user.getName()).thenReturn("login");
        when(user.getPassword()).thenReturn("password");

        UserCredentialsDTO actual = userMapper.entityToDto(user);

        assertEquals(user.getName(), actual.getLogin());
        assertEquals(user.getPassword(), actual.getPassword());
    }
}