package com.bookstack.bookstack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstack.bookstack.models.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { }

