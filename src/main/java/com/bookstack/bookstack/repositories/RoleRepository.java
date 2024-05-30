package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(ERole name);
}