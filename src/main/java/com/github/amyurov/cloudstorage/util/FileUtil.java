package com.github.amyurov.cloudstorage.util;

import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class FileUtil {
    private static final String SEP = File.separator;

    private final UserService userService;
    @Value("${app.files.directory:users_files}")
    private String filesDirectory;

    public String getUserPath() {
        return userService.getUserByContext().getPath();
    }

    /*
    Возвращет путь по которому хранится файл в памяти.
    Перегружен для возможности лишний раз не лезть в БД за пользователем
    */
    public Path getFilePath(String fileName) {
        String path = getUserPath();
        return Path.of(filesDirectory + SEP + path + SEP + fileName);
    }

    public Path getFilePath(String fileName, User user) {
        String path = user.getPath();
        return Path.of(filesDirectory + SEP + path + SEP + fileName);
    }

    // Метод возвращает MD5 хеш в виде строки
    public String getMd5HashAsString(byte[] fileBytes) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(fileBytes);
        return DatatypeConverter.printHexBinary(digest);
    }
}
