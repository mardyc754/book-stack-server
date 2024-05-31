package com.bookstack.bookstack.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Getter
    @Setter
    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Getter
    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;


    @Setter
    @Getter
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "password", nullable = false, length = 60)
    private String password;

//    @Getter
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role role;

    public User() {
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
//        this.role = role;
    }

    // getters and setters
}