package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Publisher;
import com.bookstack.bookstack.services.PublisherService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @QueryMapping
    public List<Publisher> allPublishers() {
        return publisherService.getAllPublishers();
    }

    @MutationMapping
    public Publisher addPublisher(@Argument String name) {
        return publisherService.addPublisher(name);
    }
}
