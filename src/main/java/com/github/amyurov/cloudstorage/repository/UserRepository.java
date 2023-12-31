package com.github.amyurov.cloudstorage.repository;


import com.github.amyurov.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);

    boolean existsUserByName(String username);
}
