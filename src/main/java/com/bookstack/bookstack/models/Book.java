package com.bookstack.bookstack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Book")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Book implements Serializable {

    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Getter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Getter
    @Column(name = "price", nullable = false, precision = 2)
    private double price;

    @Setter
    @Getter
    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

    @Setter
    @Getter
    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Setter
    @Getter
    @Column(name = "isbn", unique = true, nullable = false)
    private String ISBN;

    @Setter
    @Getter
    @Column(name = "description", nullable = false, length = 1000, columnDefinition = "TEXT")
    private String description;

    @Setter
    @Getter
    @Column(name = "publisher_id", insertable = false, nullable = false)
    private long publisherId;

    @Setter
    @Getter
    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false, insertable = false, updatable = false)
    private Publisher publisher;


    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "Book_Author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "Book_Category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;


    public Book() {
    }

    public Book(String title, double price, Date publicationDate, int pageCount, String ISBN, String description, Publisher publisher, List<Author> authors, List<Category> categories) {
        this.title = title;
        this.price = price;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.ISBN = ISBN;
        this.description = description;
        this.publisher = publisher;
        this.authors = authors;
        this.categories = categories;
    }

}
