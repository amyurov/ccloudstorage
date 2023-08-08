package com.github.amyurov.cloudstorage.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadRequest {

    private String hash;
    private MultipartFile file;

    public FileUploadRequest() {
    }

}
