package com.bookstack.bookstack.services;


import com.bookstack.bookstack.models.Publisher;
import com.bookstack.bookstack.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher addPublisher(String name) {
        Publisher publisher = new Publisher();
        publisher.setName(name);
        return publisherRepository.save(publisher);
    }
}
