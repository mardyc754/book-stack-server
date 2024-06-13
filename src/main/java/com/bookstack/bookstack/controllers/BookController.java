package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.repositories.AuthorRepository;
import com.bookstack.bookstack.repositories.BookRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import com.bookstack.bookstack.specs.BookSpecification;

import java.util.List;
import java.util.Optional;

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
    public Page<Book> allBooks(@Argument Optional<Integer> minQuantity) {
        Pageable pageable = PageRequest.of(0, 20);

        if (minQuantity.isPresent()) {
            BookSpecification spec = new BookSpecification(minQuantity.get());
            return bookRepository.findAllByQuantityGreaterThan(minQuantity.get(), pageable);
            //            return bookRepository.findAll(pageable, spec);
        }

        return bookRepository.findAll(pageable);
    }

    @MutationMapping
    public Book addBookToCart(@Argument Long bookId, @Argument Integer quantity) {
        return bookRepository.findById(bookId).map(book -> {
            int newQuantity = book.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Not enough books in stock");
            }
            book.setQuantity(newQuantity);
            return bookRepository.save(book);
        }).orElse(null);
    }

//    @SchemaMapping
//    public Author author(BookService bookService) {
//        return AuthorRepository.findById(bookService.authorId());
//    }
}