package com.bookstack.bookstack.dtos;

import com.bookstack.bookstack.models.Basket;
import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.BookBasket;
import com.bookstack.bookstack.models.BookBasketId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookBasketDto {

    private BookBasketId id;


    private BookDto book;

    private Basket basket;


    private int quantity;

    public BookBasketDto() {
    }

    public BookBasketDto(BookBasket bookBasket) {
        this.id = bookBasket.getId();
        this.book = new BookDto(bookBasket.getBook());
        this.basket = bookBasket.getBasket();
        this.quantity = bookBasket.getQuantity();
    }
}
