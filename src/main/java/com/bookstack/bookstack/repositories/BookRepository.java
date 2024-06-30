package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.BoughtBook;
import com.bookstack.bookstack.specs.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstack.bookstack.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
@Query("SELECT b FROM Book b JOIN b.authors a JOIN b.categories c WHERE "
        + "(b.quantity >= :minQuantity OR :minQuantity IS NULL) AND "
        + "(a.id IN :authorIds OR :authorIds IS NULL) AND "
        + "(c.id IN :categoryIds OR :categoryIds IS NULL) AND "
        + "(b.publicationDate >= :publicationDateFrom OR cast(:publicationDateFrom as date) IS NULL) AND " +
        "(b.publicationDate <= :publicationDateTo OR cast(:publicationDateTo as date) IS NULL)"
)
List<Book> findAll(
        @Param("minQuantity") Integer minQuantity,
        @Param("authorIds") List<Long> authorIds,
        @Param("categoryIds") List<Long> categoryIds,
        @Param("publicationDateFrom") LocalDate publicationDateFrom,
        @Param("publicationDateTo") LocalDate publicationDateTo
);

}

