package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.repositories.AuthorRepository;
import com.bookstack.bookstack.repositories.BookRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Page<Book> allBooks() {
        Pageable pageable = PageRequest.of(0, 20);
        return bookRepository.findAll(pageable);
    }

//    @SchemaMapping
//    public Author author(BookService bookService) {
//        return AuthorRepository.findById(bookService.authorId());
//    }
}