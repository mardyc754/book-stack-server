package com.bookstack.bookstack.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    private long id;
    private String title;
    private double price;
    private Date publicationDate;

    private int pageCount;
    private String ISBN;

    private String description;

    private int publisher;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
