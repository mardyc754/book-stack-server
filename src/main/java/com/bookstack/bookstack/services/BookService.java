package com.bookstack.bookstack.services;

import java.util.Arrays;
import java.util.List;

public record BookService(String id, String name, int pageCount, String authorId) {

    private static List<BookService> books = Arrays.asList(
            new BookService("book-1", "Effective Java", 416, "author-1"),
            new BookService("book-2", "Hitchhiker's Guide to the Galaxy", 208, "author-2"),
            new BookService("book-3", "Down Under", 436, "author-3")
    );

    public static BookService getById(String id) {
        return books.stream()
                .filter(bookService -> bookService.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
