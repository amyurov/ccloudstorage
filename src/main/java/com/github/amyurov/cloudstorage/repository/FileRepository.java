package com.github.amyurov.cloudstorage.repository;

import com.github.amyurov.cloudstorage.entity.FileEntity;
import com.github.amyurov.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    void deleteByName(String fileName);
    Optional<FileEntity> findByNameAndOwner(String fileName, User owner);

}
