package com.github.amyurov.cloudstorage.entity;

import com.github.amyurov.cloudstorage.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = true)
    private UserRole role;

    @Column(name = "file_path", nullable = false, unique = true)
    private String path;

    @OneToMany(mappedBy = "owner")
    private List<FileEntity> files;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
