package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.dtos.BookDto;
import com.bookstack.bookstack.dtos.BoughtBookDto;

import com.bookstack.bookstack.services.BookService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public BookDto bookById(@Argument Long id) {
        return bookService.bookById(id);
    }

    @QueryMapping
    public List<BookDto> allBooks(@Argument Integer minQuantity, @Argument List<Long> authorIds, @Argument List<Long> categoryIds, @Argument String publicationDateFrom, @Argument String publicationDateTo) {
        return bookService.allBooks(minQuantity, authorIds, categoryIds, publicationDateFrom, publicationDateTo);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<BoughtBookDto> boughtBooksByUserId(@Argument Long userId) {
        return bookService.boughtBooksByUserId(userId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto addBookToStock(@Argument Long bookId, @Argument Integer quantity) {
        return bookService.addBookToStock(bookId, quantity);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto changeBookPrice(@Argument Long bookId, @Argument Double newPrice) {
        return bookService.changeBookPrice(bookId, newPrice);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto addBook(
            @Argument String title,
            @Argument Double price,
            @Argument String publicationDate,
            @Argument Integer pageCount,
            @Argument String ISBN,
            @Argument String description,
            @Argument Integer quantity,
            @Argument Long publisherId,
            @Argument List<Long> authorIds,
            @Argument List<Long> categoryIds) {

        return bookService.addBook(
                title,
                price,
                LocalDate.parse(publicationDate),
                pageCount,
                ISBN,
                description,
                quantity,
                publisherId,
                authorIds,
                categoryIds);
    }
}