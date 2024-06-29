package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Author;
import com.bookstack.bookstack.services.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.getAllAuthors();
    }

    @MutationMapping
    public Author addAuthor(@Argument String firstName, @Argument  String lastName) {
        return authorService.addAuthor(firstName, lastName);
    }
}
