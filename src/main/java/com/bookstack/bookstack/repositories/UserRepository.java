package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);
}
