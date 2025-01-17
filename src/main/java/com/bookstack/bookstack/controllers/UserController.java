package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.User;
import com.bookstack.bookstack.services.JwtService;
import com.bookstack.bookstack.services.UserService;
import graphql.GraphQLContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;

        this.userService = userService;
    }

    @MutationMapping
    public User register(@Argument String username, @Argument String email, @Argument String password) {
        return userService.signup(username, email, password);
    }

    @MutationMapping
    public User login(@Argument String username, @Argument String password, GraphQLContext context){
        User authenticatedUser = userService.authenticate(username, password);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        context.put("token", jwtToken);

        return authenticatedUser;

    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean logout(GraphQLContext context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        authentication.setAuthenticated(false);
        SecurityContextHolder.clearContext();
        context.put("token", "");

        return true;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User changeUserPassword(@Argument Long userId, @Argument String newPassword) {
        return userService.changeUserPassword(userId, newPassword);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User changeUserRole(@Argument Long userId, @Argument String newRole) {
        return userService.changeUserRole(userId, newRole);
    }
}