package com.github.amyurov.cloudstorage.config;

import com.github.amyurov.cloudstorage.jwt.JwtFilter;
import com.github.amyurov.cloudstorage.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandler;

    @Bean // Настроим цепочку фильтров SpringSecurity
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/login", "/registration", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().addLogoutHandler(logoutHandler).logoutSuccessHandler((request, response, authentication) ->
                        SecurityContextHolder.clearContext())
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean // Настройка CORS
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean //Укажем нужный нам шифровщик паролей
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
        AuthenticationManager - апи для прохождения аутентификации.
        ProviderManager - основная реализация, в которой хранится цепочка поставщиков аутентификации.
        DAOAuthenticationProvider - стандартный поставщик, работающий с базовой аутентификацией.
        UserDetailsService - единая точка доступа, через которую SpringSecurity получает нужный UserDetails
    */
    @Bean
    public AuthenticationManager authenticationManager() {
        var provider = new DaoAuthenticationProvider();
        // Укажем поставщику шифровщик паролей и UserDetailsService
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);

        return new ProviderManager(provider);
    }
}
