package com.github.amyurov.cloudstorage.controller;

import com.github.amyurov.cloudstorage.mapper.UserMapper;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import com.github.amyurov.cloudstorage.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return registrationService.saveUser(userMapper.dtoToEntity(userCredentialsDTO));
    }
}
