package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, BookBasketRepository> {

    void deleteByBookIdAndBasketId(Long bookId, Long basketId);
}
