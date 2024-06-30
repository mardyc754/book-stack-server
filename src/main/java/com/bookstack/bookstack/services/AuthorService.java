package com.bookstack.bookstack.services;

import com.bookstack.bookstack.models.Author;
import com.bookstack.bookstack.repositories.AuthorRepository;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(StringUtils.capitalize(firstName));
        author.setLastName(StringUtils.capitalize(lastName));
        return authorRepository.save(author);
    }
}
