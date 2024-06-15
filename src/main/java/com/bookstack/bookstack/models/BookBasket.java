package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "book_basket")
public class BookBasket {

    @EmbeddedId
    @Getter
    @Setter
    private BookBasketId id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("basketId")
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Getter
    @Setter
    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    public BookBasket() {
    }

    public BookBasket(Book book, Basket basket, int quantity) {
        this.book = book;
        this.basket = basket;
        this.quantity = quantity;
    }

}
