package com.github.amyurov.cloudstorage.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    private static final String FILENAME = "fileName";
    @Mock
    private FileService fileService;
    @InjectMocks
    private FileController fileController;

    @Test
    void showFiles_shouldCallService() {
        int limit = 3;

        fileController.showFiles(3);

        Mockito.verify(fileService).showUserFiles(limit);
    }

    @Test
    void uploadFile_shouldCallService() {
        final MultipartFile file = mock(MultipartFile.class);

        fileController.uploadFile(FILENAME, file);

        verify(fileService).uploadFile(FILENAME, file);
    }


    @Test
    void deleteFile_shouldCallService() {

        fileController.deleteFile(FILENAME);

        verify(fileService).deleteFIle(FILENAME);
    }

    @Test
    void updateFile() {
        final UpdateFileData fileData = mock(UpdateFileData.class);

        fileController.updateFile(FILENAME, fileData);

        verify(fileService).updateFile(FILENAME, fileData);
    }
}