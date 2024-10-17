package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

}
