package com.bookstack.bookstack.services;

import com.bookstack.bookstack.dtos.BookDto;
import com.bookstack.bookstack.dtos.BoughtBookDto;
import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BoughtBookRepository boughtBookRepository;

    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository,
                       BoughtBookRepository boughtBookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       CategoryRepository categoryRepository
    ) {
        this.bookRepository = bookRepository;
        this.boughtBookRepository = boughtBookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    public BookDto bookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        return new BookDto(book);
    }

    public List<BookDto> allBooks(Integer minQuantity, List<Long> authorIds, List<Long> categoryIds, String publicationDateFrom, String publicationDateTo) {
        LocalDate pubDateFrom = (publicationDateFrom != null) ? LocalDate.parse(publicationDateFrom) : null;
        LocalDate pubDateTo = (publicationDateTo != null) ? LocalDate.parse(publicationDateTo) : null;
        return BookDto.fromBooks(bookRepository.findAll(minQuantity, authorIds, categoryIds, pubDateFrom, pubDateTo));
    }

    public List<BoughtBookDto> boughtBooksByUserId(Long userId) {
        return BoughtBookDto.fromBoughtBooks(boughtBookRepository.findBoughtBooksByUserId(userId));
    }

    public BookDto addBookToStock(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        book.setQuantity(book.getQuantity() + quantity);
        return new BookDto(bookRepository.save(book));
    }

    public BookDto changeBookPrice(Long bookId, Double newPrice) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        book.setPrice(newPrice);
        return new BookDto(bookRepository.save(book));
    }

    public BookDto addBook(
            String title,
            Double price,
            LocalDate publicationDate,
            Integer pageCount,
            String ISBN,
            String description,
            Integer quantity,
            Long publisherId,
            List<Long> authorIds,
            List<Long> categoryIds
    ) {
        List <Author> authors = authorRepository.findAllById(authorIds);

        if (authors.stream().findFirst().isEmpty()) {
            throw new IllegalArgumentException("Author not found");
        }

        List <Category> categories = categoryRepository.findAllById(categoryIds);

        if (authors.stream().findFirst().isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }

        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);

        if (publisher == null) {
            throw new IllegalArgumentException("Publisher not found");
        }

        return new BookDto(bookRepository.save(new Book(
                title,
                price,
                publicationDate,
                pageCount,
                ISBN,
                description,
                publisher,
                authors,
                categories,
                quantity
        )));
    }
}
