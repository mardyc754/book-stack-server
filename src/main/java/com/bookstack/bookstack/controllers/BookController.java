package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.services.AuthorService;
import com.bookstack.bookstack.services.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {
    @QueryMapping
    public BookService bookById(@Argument String id) {
        return BookService.getById(id);
    }

    @SchemaMapping
    public AuthorService author(BookService bookService) {
        return AuthorService.getById(bookService.authorId());
    }
}