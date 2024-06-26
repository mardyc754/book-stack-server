package com.bookstack.bookstack.specs;

import com.bookstack.bookstack.models.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification implements Specification<Book> {
    private final int minQuantity;

    public BookSpecification(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (minQuantity <= 0) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // No filtering if minQuantity is not positive
        }
        return criteriaBuilder.greaterThan(root.get("quantity"), this.minQuantity);
    }
}
