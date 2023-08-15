package com.github.amyurov.cloudstorage.mapper;

import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements SimpleMapper<User, UserCredentialsDTO> {
    @Override
    public User dtoToEntity(UserCredentialsDTO userCredentialsDTO) {
        User user = new User();
        user.setName(userCredentialsDTO.getLogin());
        user.setPassword(userCredentialsDTO.getPassword());
        return user;
    }

    @Override
    public UserCredentialsDTO entityToDto(User user) {
        return new UserCredentialsDTO(user.getName(), user.getPassword());
    }
}
