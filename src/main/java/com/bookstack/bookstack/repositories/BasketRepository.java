package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByUserId(Long userId);
}
