package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.dtos.BasketDto;
import com.bookstack.bookstack.services.BasketService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class BasketController {

    private final BasketService basketService;


    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BasketDto addBookToCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        return basketService.addBookToCart(bookId, userId, quantity);
    }


    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BasketDto changeBookQuantityInCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        return basketService.changeBookQuantityInCart(bookId, userId, quantity);
    }

    @MutationMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BasketDto removeBookFromCart(@Argument Long bookId, @Argument Long userId) {
       return basketService.removeBookFromCart(bookId, userId);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BasketDto basketByUserId(@Argument Long userId) {

        return basketService.basketByUserId(userId);
    }

    @MutationMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BasketDto buyBooks(@Argument Long userId) {
        return basketService.buyBooks(userId);
    }


}