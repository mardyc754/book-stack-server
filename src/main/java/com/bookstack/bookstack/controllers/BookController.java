package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final BoughtBookRepository boughtBookRepository;

    public BookController(BookRepository bookRepository, BoughtBookRepository boughtBookRepository) {
        this.bookRepository = bookRepository;
        this.boughtBookRepository = boughtBookRepository;
    }


    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Page<Book> allBooks(@Argument Optional<Integer> minQuantity) {
        Pageable pageable = PageRequest.of(0, 20);

        if (minQuantity.isPresent()) {
            return bookRepository.findAllByQuantityGreaterThan(minQuantity.get(), pageable);
            //            return bookRepository.findAll(pageable, spec);
        }

        return bookRepository.findAll(pageable);
    }

    @QueryMapping
    public List<BoughtBook> boughtBooksByUserId(@Argument Long userId) {
        return boughtBookRepository.findBoughtBooksByUserId(userId);
    }
}