package com.github.amyurov.cloudstorage.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.amyurov.cloudstorage.error.AppError;
import com.github.amyurov.cloudstorage.exception.RetrievedJWTException;
import com.github.amyurov.cloudstorage.user.User;
import com.github.amyurov.cloudstorage.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


// Фильтр, который будет проверять валидность токена, чтобы
// держатель токена мог пользоватья закрытыми ресурсами приложения
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final UserService userService;
    @Value("${app.jwt.token_header:auth-token}")
    private String authHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Проверим передается ли токен в запросе, если нет продолжим выполнение фильтров
        if (request.getHeader(authHeader) == null) {
            log.warn("Запрос не содержит токена авторизации");
            filterChain.doFilter(request, response);
            return;
        }

        // Получим токен из пришедшего запроса
        String token = request.getHeader(authHeader).substring(7);

        // Проверим не отозван ли токен
        if (jwtService.isRetrieved(token)) {
            log.info("Запрос содержит запрещенный токен авторизации");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            filterChain.doFilter(request, response);
            return;
        }

        String username = "";
        // Проверим токен на валидность
        try {
            username = jwtUtil.getName(token);
        } catch (JWTVerificationException e) {
            log.error("Запрос содержит невалидный токен авторизации");
            filterChain.doFilter(request, response);
        }

        // Если все нормально, поместим пользователя в SecurityContext
        User user = userService.findByName(username);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        var userDetails = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(userDetails);

        //Передадим фильтры дальше по цепочке
        filterChain.doFilter(request, response);
    }

    @ExceptionHandler(RetrievedJWTException.class)
    public ResponseEntity<AppError> handleRetrievedJWTException() {
        return new ResponseEntity<>(new AppError("Ошибка при обработке ткоенаа"), HttpStatus.UNAUTHORIZED);
    }
}
