package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.BookBasket;
import com.bookstack.bookstack.models.BoughtBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoughtBookRepository  extends JpaRepository<BoughtBook, BoughtBookRepository> {
    Optional<BoughtBook> findByBookIdAndUserId(Long bookId, Long userId);

    List<BoughtBook> findBoughtBooksByUserId(Long userId);

}

