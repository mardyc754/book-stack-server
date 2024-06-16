package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.BoughtBook;
import com.bookstack.bookstack.specs.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstack.bookstack.models.Book;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    Page<Book> findAll(Pageable pageable);
    @NonNull
    Page<Book> findAll(@NonNull Pageable pageable);

    Page<Book> findAllByQuantityGreaterThan(Integer integer, Pageable pageable);

}

