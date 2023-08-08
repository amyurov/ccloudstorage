package com.github.amyurov.cloudstorage.error;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AppError {
    private String message;
    private String id;

    public AppError(String message) {
        this.message = message;
        this.id = UUID.randomUUID().toString();
    }
}
