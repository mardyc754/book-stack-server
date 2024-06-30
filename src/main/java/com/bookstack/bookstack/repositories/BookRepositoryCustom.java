package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findBooksByCriteria(Integer minQuantity, List<Long> authorIds, List<Long> categoryIds, LocalDate publicationDate);
}