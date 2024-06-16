package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bought_book")
public class BoughtBook {

    @EmbeddedId
    private BoughtBookId id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    public BoughtBook() {
    }

    public BoughtBook(Book book, User user, int quantity) {
        this.book = book;
        this.user = user;
        this.quantity = quantity;
    }


}
