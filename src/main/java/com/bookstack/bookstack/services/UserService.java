package com.bookstack.bookstack.services;

import com.bookstack.bookstack.models.User;
import com.bookstack.bookstack.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {

        List<User> users = new ArrayList<>(userRepository.findAll());

        return users;
    }
}