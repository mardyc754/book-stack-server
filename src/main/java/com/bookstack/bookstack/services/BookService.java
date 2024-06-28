package com.bookstack.bookstack.services;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.BoughtBook;
import com.bookstack.bookstack.repositories.BookRepository;
import com.bookstack.bookstack.repositories.BoughtBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BoughtBookRepository boughtBookRepository;

    public BookService(BookRepository bookRepository, BoughtBookRepository boughtBookRepository) {
        this.bookRepository = bookRepository;
        this.boughtBookRepository = boughtBookRepository;
    }


    public Book bookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> allBooks(Optional<Integer> minQuantity) {

        if (minQuantity.isPresent()) {
            return bookRepository.findAllByQuantityGreaterThan(minQuantity.get());
        }

        return bookRepository.findAll();
    }

    public List<BoughtBook> boughtBooksByUserId(Long userId) {
        return boughtBookRepository.findBoughtBooksByUserId(userId);
    }

    public Book addBookToStock(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        book.setQuantity(book.getQuantity() + quantity);
        return bookRepository.save(book);
    }

    public Book changeBookPrice(Long bookId, Double newPrice) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        book.setPrice(newPrice);
        return bookRepository.save(book);
    }
}
