package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role() {
    }
    public Role(ERole name) {
        this.name = name;
    }
}
