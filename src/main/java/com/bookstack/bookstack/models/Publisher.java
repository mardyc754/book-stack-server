package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Publisher")
public class Publisher {

    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher", targetEntity = Book.class)
    private List<Book> books;



}
