package com.github.amyurov.cloudstorage;

import com.github.amyurov.cloudstorage.registration.RegistrationService;
import com.github.amyurov.cloudstorage.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class CloudStorageApplication implements CommandLineRunner {
    private final RegistrationService registrationService;
    @Value("${app.files.directory:users_files}")
    private String filesDirectory;
    @Value("${app.default_user.username:user@mail.ru}")
    private String username;
    @Value("${app.default_user.password:password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner CommandLineRunnerBean() {
//        return (args) -> {
//            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+$");
//            Matcher matcher = pattern.matcher(filesDirectory);
//            if (!matcher.matches()) {
//                log.error("Имя директории содержит недопустимые символы. Допускаются только буквы и цифры");
//            }
//
//            File filesDir = new File(filesDirectory);
//            if (filesDir.exists()) {
//                log.info("Директория файлов определена " + filesDirectory);
//            } else if (filesDir.mkdir()) {
//                log.info("Директория файлов создана " + filesDirectory);
//            }
//            registerDefaultUser(username, password);
//            log.info("\nПользователь по умолчанию:\n" +
//                    "username: " + username + "\n" +
//                    "password: " + password);
//        };
//    }


    @Override
    public void run(String... args) throws Exception {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+$");
        Matcher matcher = pattern.matcher(filesDirectory);
        if (!matcher.matches()) {
            log.error("Имя директории содержит недопустимые символы. Допускаются только буквы и цифры");
        }

        File filesDir = new File(filesDirectory);
        if (filesDir.exists()) {
            log.info("Директория файлов определена " + filesDirectory);
        } else if (filesDir.mkdir()) {
            log.info("Директория файлов создана " + filesDirectory);
        }
        registerDefaultUser(username, password);
        log.info("\nПользователь по умолчанию:\n" +
                "username: " + username + "\n" +
                "password: " + password);
    }

    private void registerDefaultUser(String username, String password) {
        registrationService.saveUser(new User(username, password));
    }
}
