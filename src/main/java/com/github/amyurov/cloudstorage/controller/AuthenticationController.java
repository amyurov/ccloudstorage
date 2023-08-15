package com.github.amyurov.cloudstorage.controller;

import com.github.amyurov.cloudstorage.error.AppError;
import com.github.amyurov.cloudstorage.dto.JwtResponse;
import com.github.amyurov.cloudstorage.dto.UserCredentialsDTO;
import com.github.amyurov.cloudstorage.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> auth(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return authenticationService.authenticate(userCredentialsDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppError> bcExceptionHandler() {
        return new ResponseEntity<>(new AppError("Неверные пользовательские данные"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AppError> authExceptionHandler() {
        return new ResponseEntity<>(new AppError("Ошибка аутентификации"), HttpStatus.BAD_REQUEST);
    }
}
