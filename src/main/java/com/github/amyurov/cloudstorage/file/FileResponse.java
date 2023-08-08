package com.github.amyurov.cloudstorage.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {
    private String hash;
    private byte[] file;

    public FileResponse(String hash, byte[] file) {
        this.hash = hash;
        this.file = file;
    }
}
