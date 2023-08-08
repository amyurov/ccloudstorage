package com.github.amyurov.cloudstorage.file;

import com.github.amyurov.cloudstorage.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    void deleteByName(String fileName);

    Optional<FileEntity> findByName(String fileName);
    Optional<FileEntity> findByNameAndOwner(String fileName, User owner);

}
