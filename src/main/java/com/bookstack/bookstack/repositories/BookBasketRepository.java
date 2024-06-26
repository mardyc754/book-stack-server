package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, BookBasketRepository> {

    void deleteByBookIdAndBasketId(Long bookId, Long basketId);

    Optional<BookBasket> findByBookIdAndBasketId(Long bookId, Long basketId);
}
