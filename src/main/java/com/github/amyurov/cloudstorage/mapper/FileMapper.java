package com.github.amyurov.cloudstorage.mapper;

import com.github.amyurov.cloudstorage.dto.FileDTO;
import com.github.amyurov.cloudstorage.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper implements SimpleMapper<FileEntity, FileDTO> {

    @Override
    public FileEntity dtoToEntity(FileDTO fileDTO) {
        return null;
    }

    @Override
    public FileDTO entityToDto(FileEntity fileEntity) {
        return new FileDTO(fileEntity.getName(), fileEntity.getSize());
    }

    public FileMapper() {
    }
}

