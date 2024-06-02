package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(String name);
}