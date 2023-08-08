package com.github.amyurov.cloudstorage.registration;

import com.github.amyurov.cloudstorage.error.AppError;
import com.github.amyurov.cloudstorage.user.User;
import com.github.amyurov.cloudstorage.user.UserRepository;
import com.github.amyurov.cloudstorage.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Value("${app.files.directory}")
    private String filesDirectory;

    @Transactional
    public ResponseEntity<?> saveUser(User user) {

        if (userRepository.existsUserByName(user.getName())) {
            return new ResponseEntity<>(new AppError(String.format("Пользователь %s уже существует!",
                    user.getName())), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);

        String path = getDirectoryName(user.getName());
        user.setPath(path);
        createUsersDirectory(path, user.getName());

        userRepository.save(user);
        log.info("Пользователь " + user.getName() + " зарегестрирован");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод для генерации названия директории для хранения файлов
    private String getDirectoryName(String username) {
        return username.replaceAll("[^a-zA-Z0-9]", "");
    }

    private void createUsersDirectory(String path, String username) {
        String sep = File.separator;
        File userFileDir = new File(filesDirectory + sep + path);
        if (userFileDir.mkdir()) {
            log.info("Для пользователя " + username + "создана директория " + userFileDir.getPath());
        }
    }
}
