package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.List;


@Entity
@Table(name = "book_basket")
public class BookBasket {

    @Id
    @Getter
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Long bookId;

    @Id
    @Getter
    @ManyToOne(targetEntity = Basket.class)
    @JoinColumn(name = "basket_id", nullable = false, insertable = false, updatable = false)
    private Long basketId;

    @Getter
    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    public BookBasket() {
    }

    public BookBasket(Long bookId, Long basketId, int quantity) {
        this.bookId = bookId;
        this.basketId = basketId;
        this.quantity = quantity;
    }

}
