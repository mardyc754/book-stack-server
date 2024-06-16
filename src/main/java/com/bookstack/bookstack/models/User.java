package com.bookstack.bookstack.models;

import java.util.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    String ROLE_PREFIX = "ROLE_";

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

    @Getter
    @Setter
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    private List<BoughtBook> boughtBooks;

    @Getter
    @Setter
    @OneToOne(mappedBy = "user", targetEntity = Basket.class)
    // @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

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

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return authorities;
    }


    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.boughtBooks = new ArrayList<>();
        this.role = role;
    }

    // getters and setters
}