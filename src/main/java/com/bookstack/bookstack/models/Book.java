package com.bookstack.bookstack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Book")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Book implements Serializable {

    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
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
    @Column(name = "image_url_s", nullable = false)
    private String imageUrlS;

    @Setter
    @Getter
    @Column(name = "image_url_m", nullable = false)
    private String imageUrlM;

    @Setter
    @Getter
    @Column(name = "image_url_l", nullable = false)
    private String imageUrlL;

    @Setter
    @Getter
    @Column(name = "isbn", unique = true, nullable = false)
    private String ISBN;

    @Setter
    @Getter
    @Column(name = "description", nullable = true, length = 1000, columnDefinition = "TEXT")
    private String description;


    @Setter
    @Getter
    @ManyToOne(targetEntity = Publisher.class)
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

    @Setter
    @Getter
    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "Quantity must be greater or equal 0")
    private int quantity;

    @OneToMany(mappedBy = "book")
    private List<BookBasket> bookBasket = new ArrayList<>();


    public Book() {
    }

    public Book(String title,
                double price,
                Date publicationDate,
                int pageCount,
                String ISBN,
                String description,
                Publisher publisher,
                List<Author> authors,
                List<Category> categories,
                String imageUrlS,
                String imageUrlM,
                String imageUrlL,
                int quantity
    ) {
        this.title = title;
        this.price = price;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.ISBN = ISBN;
        this.description = description;
        this.publisher = publisher;
        this.authors = authors;
        this.categories = categories;
        this.imageUrlS = imageUrlS;
        this.imageUrlM = imageUrlM;
        this.imageUrlL = imageUrlL;
        this.quantity = quantity;
    }

}
