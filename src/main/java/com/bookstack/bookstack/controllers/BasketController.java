package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.services.BasketService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class BasketController {

    private final BasketService basketService;


    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @MutationMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket addBookToCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        return basketService.addBookToCart(bookId, userId, quantity);
    }


    @MutationMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket changeBookQuantityInCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        return basketService.changeBookQuantityInCart(bookId, userId, quantity);
    }

    @MutationMapping
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket removeBookFromCart(@Argument Long bookId, @Argument Long userId) {
       return basketService.removeBookFromCart(bookId, userId);
    }

    @QueryMapping
    public Basket basketByUserId(@Argument Long userId) {

        return basketService.basketByUserId(userId);
    }

    @MutationMapping
    @Transactional
    public Basket buyBooks(@Argument Long userId) {
        return basketService.buyBooks(userId);
    }


}