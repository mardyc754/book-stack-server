package com.bookstack.bookstack.dtos;

import com.bookstack.bookstack.models.Basket;
import com.bookstack.bookstack.models.BookBasket;
import com.bookstack.bookstack.models.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BasketDto {
    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @OneToMany(mappedBy = "basket")
    @Setter
    private List<BookBasketDto> books;

    public BasketDto() {
    }

    public BasketDto(Basket basket) {
        this.id = basket.getId();
        this.user = basket.getUser();
        if (basket.getBooks() == null) {
            return;
        }
        this.books = new ArrayList<>();
        for (BookBasket bookBasket : basket.getBooks()) {
            this.books.add(new BookBasketDto(bookBasket));
        }
    }


}
