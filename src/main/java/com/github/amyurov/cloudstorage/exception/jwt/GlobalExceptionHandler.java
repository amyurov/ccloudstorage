package com.github.amyurov.cloudstorage.exception.jwt;

import com.github.amyurov.cloudstorage.error.AppError;
import com.github.amyurov.cloudstorage.exception.jwt.RetrievedJWTException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RetrievedJWTException.class)
    public AppError handleAuthenticationException(RetrievedJWTException ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new AppError("Запрос содержит запрещенный токен");
    }
}
