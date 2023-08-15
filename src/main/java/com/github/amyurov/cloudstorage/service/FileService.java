package com.github.amyurov.cloudstorage.service;

import com.github.amyurov.cloudstorage.dto.FileDTO;
import com.github.amyurov.cloudstorage.entity.FileEntity;
import com.github.amyurov.cloudstorage.error.AppError;
import com.github.amyurov.cloudstorage.util.FileUtil;
import com.github.amyurov.cloudstorage.dto.UpdateFileData;
import com.github.amyurov.cloudstorage.mapper.FileMapper;
import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileRepository fileRepository;
    private final UserService userService;
    private final FileMapper fileMapper;
    private final FileUtil fileUtil;


    public List<FileDTO> showUserFiles(int limit) {
        // Имя пользователя из контекста
        User user = userService.getUserByContext();
        return user.getFiles().stream()
                .map(fileMapper::entityToDto)
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Метод для сохранения файла
    @Transactional
    public ResponseEntity<?> uploadFile(String fileName, MultipartFile file) {
        // Проверим правильность введенного fileName (должно быть с расширением)
        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.find()) {
            return ResponseEntity
                    .status(400)
                    .body(new AppError("Имя файла должно иметь расширение"));
        }

        // Получим путь пользователя, который загружает файл
        User user = userService.getUserByContext();
        try {
            saveFileInMemory(fileName, file, user);
            saveFileEntity(user, file);
        } catch (IOException e) {
            return ResponseEntity
                    .status(500)
                    .body(new AppError("Ошибка при чтении данных из файла"));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод созраняет файл в память
    private void saveFileInMemory(String fileName, MultipartFile file, User user) throws IOException {
        Path filePath = fileUtil.getFilePath(fileName, user);
        Files.write(filePath, file.getBytes());
        log.info(user.getName() + " добавил файл " + fileName);
    }

    // Метод сохраняет сущность в таблицу
    private void saveFileEntity(User user, MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        String hash = fileUtil.getMd5HashAsString(file.getBytes());

        fileEntity.setHash(hash);
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setSize((int) file.getSize());
        fileEntity.setOwner(user);

        fileRepository.save(fileEntity);
    }

    // Метод удалет файл из памяти и БД
    @Transactional
    public ResponseEntity<?> deleteFIle(String fileName) {
        Path filePath = fileUtil.getFilePath(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Ошибка при удалении файла");
            return ResponseEntity
                    .status(500)
                    .body(new AppError("Ошибка при удалении файла"));
        }

        fileRepository.deleteByName(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод возвращает файл для скачивания
    public FileSystemResource getFile(String fileName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Path filePath = fileUtil.getFilePath(fileName);
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        log.info(username + " запросил файл " + fileName);
        return fileSystemResource;
    }

    // Метод обновляет данные о файле (переименовывает)
    @Transactional
    public void updateFile(String fileName, UpdateFileData dataToUpdate) {
        String uploadFileName = dataToUpdate.getFilename();
        User user = userService.getUserByContext();
        Path fileToUpdatePath = fileUtil.getFilePath(fileName, user);
        Path updatedFilePath = fileUtil.getFilePath(uploadFileName, user);

        File file = new File(fileToUpdatePath.toUri());
        file.renameTo(updatedFilePath.toFile());

        FileEntity fileEntity = fileRepository.findByNameAndOwner(fileName, user).orElseThrow(EntityNotFoundException::new);
        assert fileEntity != null;
        fileEntity.setName(dataToUpdate.getFilename());
        fileRepository.save(fileEntity);
    }
}
