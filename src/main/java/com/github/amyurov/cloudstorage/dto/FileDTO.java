package com.github.amyurov.cloudstorage.dto;

import lombok.Data;

@Data
public class FileDTO {
    private String filename;
    private int size;

    public FileDTO(String filename, int size) {
        this.filename = filename;
        this.size = size;
    }
}

