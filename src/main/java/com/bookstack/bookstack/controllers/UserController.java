package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.User;
import com.bookstack.bookstack.services.AuthenticationService;
import com.bookstack.bookstack.services.JwtService;
import com.bookstack.bookstack.utils.LoginResponse;
import graphql.GraphQLContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    public UserController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @MutationMapping
    public User register(@Argument String username, @Argument String email, @Argument String password) {
        return authenticationService.signup(username, email, password);
    }

    @MutationMapping
    public User login(@Argument String username, @Argument String password, GraphQLContext context){
        User authenticatedUser = authenticationService.authenticate(username, password);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        context.put("token", jwtToken);

        return authenticatedUser;

    }
}