package com.github.amyurov.cloudstorage.mapper;

import com.github.amyurov.cloudstorage.user.User;
import com.github.amyurov.cloudstorage.user.UserCredentialsDTO;
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
