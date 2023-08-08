package com.github.amyurov.cloudstorage.file;

import com.github.amyurov.cloudstorage.error.AppError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;
    @GetMapping("/list")
    public List<FileDTO> showFiles(@RequestParam("limit") int limit) {
        return fileService.showUserFiles(limit);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("filename") String fileName,
                                        @RequestBody MultipartFile file) {
        return fileService.uploadFile(fileName, file);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam("filename") String fileName) {
        fileService.deleteFIle(fileName);
    }

    @GetMapping(value = "/file")
    public Resource getFile(@RequestParam("filename") String fileName) throws IOException {
        return fileService.getFile(fileName);
    }

    @PutMapping("/file")
    public void updateFile(@RequestParam("filename") String fileName,
                           @RequestBody UpdateFileData data) {
        fileService.updateFile(fileName, data);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<AppError> fnfException() {
        log.error("Не удалось найти файл");
        return new ResponseEntity<>(new AppError("Файла с данным именем не найден"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppError> ioException() {
        log.error("Ошибка при обработке файла");
        return new ResponseEntity<>(new AppError("Ошибка при обработке файла"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
