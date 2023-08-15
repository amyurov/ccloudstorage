package com.github.amyurov.cloudstorage.file;

import com.github.amyurov.cloudstorage.entity.User;
import com.github.amyurov.cloudstorage.service.UserService;
import com.github.amyurov.cloudstorage.util.FileUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
class FileUtilTest {
    private static final String SEP = File.separator;
    @Mock
    private UserService userService;
    @Value("${app.files.directory:users_files}")
    private String filesDirectory;
    @InjectMocks
    private FileUtil fileUtil;

    @Test
    void getUserPath_shouldCallService() {
        final User user = mock(User.class);
        user.setPath("user_path");
        when(userService.getUserByContext()).thenReturn(user);

        String actual = fileUtil.getUserPath();

        verify(userService).getUserByContext();
        assertEquals(user.getPath(), actual);
    }

    @Test
    void getFilePath() {
        final User user = mock(User.class);
        user.setPath("user_path");
        String fileName = "file_name";
        String expected = filesDirectory + SEP + user.getPath() + SEP + fileName;

        Path filePath = fileUtil.getFilePath(fileName, user);
        String actual = filePath.toString();

        assertEquals(expected, actual);
    }

}