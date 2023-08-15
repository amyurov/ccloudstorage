package com.github.amyurov.cloudstorage.repository;

import com.github.amyurov.cloudstorage.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<JwtEntity, Integer> {

    Optional<JwtEntity> findByToken(String token);

    boolean existsJwtEntityByToken(String token);

    @Query(nativeQuery = true, value = "DELETE FROM Retrieved_tokens WHERE expiration_date < NOW()")
    void removeExpired();

}
