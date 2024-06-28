package com.bookstack.bookstack.services;

import com.bookstack.bookstack.models.User;
import com.bookstack.bookstack.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
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

    public User changeUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public User changeUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElseThrow();
        List<User> admins = userRepository.findAllByRole("ADMIN");
        if (newRole.equals("USER") && admins.size() == 1 && admins.getFirst().getId().equals(userId)) {
            throw new IllegalArgumentException("Cannot remove the last admin");
        }
        user.setRole(newRole);
        return userRepository.save(user);
    }
}