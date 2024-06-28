package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.*;

import com.bookstack.bookstack.services.BookService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookService.bookById(id);
    }

    @QueryMapping
    public List<Book> allBooks(@Argument Optional<Integer> minQuantity) {
        return bookService.allBooks(minQuantity);
    }

    @QueryMapping
    public List<BoughtBook> boughtBooksByUserId(@Argument Long userId) {
        return bookService.boughtBooksByUserId(userId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Book addBookToStock(@Argument Long bookId, @Argument Integer quantity) {
        return bookService.addBookToStock(bookId, quantity);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Book changeBookPrice(@Argument Long bookId, @Argument Double newPrice) {
        return bookService.changeBookPrice(bookId, newPrice);
    }
}