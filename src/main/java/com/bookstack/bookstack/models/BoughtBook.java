package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Entity
@Table(name = "bought_book")
public class BoughtBook {

    @Id
    @Getter
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Long bookId;

    @Id
    @Getter
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Getter
    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    public BoughtBook() {
    }

    public BoughtBook(Long bookId, Long userId, int quantity) {
        this.bookId = bookId;
        this.userId = userId;
        this.quantity = quantity;
    }
}
