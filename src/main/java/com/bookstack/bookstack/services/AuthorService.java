package com.bookstack.bookstack.services;

import java.util.Arrays;
import java.util.List;

public record AuthorService(String id, String firstName, String lastName) {

    private static List<AuthorService> authorServices = Arrays.asList(
            new AuthorService("author-1", "Joshua", "Bloch"),
            new AuthorService("author-2", "Douglas", "Adams"),
            new AuthorService("author-3", "Bill", "Bryson")
    );

    public static AuthorService getById(String id) {
        return authorServices.stream()
                .filter(authorService -> authorService.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}