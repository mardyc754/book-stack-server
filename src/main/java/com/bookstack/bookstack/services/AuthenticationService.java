package com.bookstack.bookstack.services;

import com.bookstack.bookstack.dtos.LoginUserDto;
import com.bookstack.bookstack.dtos.RegisterUserDto;
import com.bookstack.bookstack.models.Role;
import com.bookstack.bookstack.models.User;
import com.bookstack.bookstack.repositories.RoleRepository;
import com.bookstack.bookstack.repositories.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User signup(String username, String email, String password) {

        User user = new User(username, email, passwordEncoder.encode(password), "USER");

        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        return userRepository.findByUsername(username).orElseThrow();

    }
}