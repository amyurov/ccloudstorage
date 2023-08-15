package com.github.amyurov.cloudstorage.service;

import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override //Метод который возвращает сущность, в обертке для SpringSecurity - UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("user %s not found", username)
        ));
        // Получим роль нашей сущности в специальной обертке
        var role = new SimpleGrantedAuthority(user.getRole().toString());

        //Обернем данные сущности в UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(),
                Collections.singletonList(role));
    }

    public User findByName(String username) {
        return userRepository.findByName(username).orElse(null);
    }

    public User getUserByContext() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(username).orElse(null);
    }

}
