package com.github.amyurov.cloudstorage.dto;

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
