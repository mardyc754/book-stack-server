package com.bookstack.bookstack.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookstack.bookstack.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}