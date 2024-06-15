package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;
    private final BookBasketRepository bookBasketRepository;

    private final UserRepository userRepository;

    public BookController(BookRepository bookRepository, BasketRepository basketRepository, UserRepository userRepository, BookBasketRepository bookBasketRepository) {
        this.bookRepository = bookRepository;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.bookBasketRepository = bookBasketRepository;
    }


    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Page<Book> allBooks(@Argument Optional<Integer> minQuantity) {
        Pageable pageable = PageRequest.of(0, 20);

        if (minQuantity.isPresent()) {
            return bookRepository.findAllByQuantityGreaterThan(minQuantity.get(), pageable);
            //            return bookRepository.findAll(pageable, spec);
        }

        return bookRepository.findAll(pageable);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket addBookToCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        // Remove the book from the stock
        User user = userRepository.findById(userId).orElse(null);

        Book bookAddedToBasket = bookRepository.findById(bookId).map(book -> {
            int newQuantity = book.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Not enough books in stock");
            }
            book.setQuantity(newQuantity);
            return bookRepository.save(book);
        }).orElse(null);

        if (user == null || bookAddedToBasket == null) {
            throw new IllegalArgumentException("User or book not found");
        }

        Basket userBasket = user.getBasket();

        if (userBasket == null) {
            userBasket = new Basket();
            userBasket.setUser(user);
            Basket createdBasket = basketRepository.save(userBasket);

            BookBasketId id = new BookBasketId();
            id.setBookId(bookId);
            id.setBasketId(createdBasket.getId());
            BookBasket bookBasket = new BookBasket(bookAddedToBasket, createdBasket, quantity);
            bookBasket.setId(id);
            bookBasketRepository.save(bookBasket);

            createdBasket.setBooks(List.of(bookBasket));
            return basketRepository.save(createdBasket);
        } else {
            List<BookBasket> books = userBasket.getBooks();
            boolean bookAlreadyInBasket = false;
            for (BookBasket book : books) {
                if (book.getBook().getId().equals(bookId)) {
                    book.setQuantity(book.getQuantity() + quantity);
                    bookAlreadyInBasket = true;
                    break;
                }
            }
            if (!bookAlreadyInBasket) {
                BookBasketId id = new BookBasketId();
                id.setBookId(bookAddedToBasket.getId());
                id.setBasketId(userBasket.getId());
                BookBasket bookBasket = new BookBasket(bookAddedToBasket, userBasket, quantity);
                bookBasket.setId(id);
                bookBasketRepository.save(bookBasket);
            }
            userBasket.setBooks(books);
        }

        return basketRepository.save(userBasket);
    }


    @MutationMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket changeBookQuantityInCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Basket userBasket = user.getBasket();

        if (userBasket == null) {
            throw new IllegalArgumentException("Basket not found");
        }

        List<BookBasket> books = userBasket.getBooks();
        boolean bookInBasket = false;
        for (BookBasket book : books) {
            if (book.getBook().getId().equals(bookId)) {
                int oldQuantity = book.getQuantity();
                book.setQuantity(quantity);
                int bookQuantityInStock = book.getBook().getQuantity();
                int newQuantity = book.getQuantity();
                book.getBook().setQuantity(bookQuantityInStock + (newQuantity - oldQuantity));
                bookInBasket = true;
                break;
            }

            if (book.getQuantity() < 1) {
                books.remove(book);
            }
        }
        if (!bookInBasket) {
            throw new IllegalArgumentException("Book not found in basket");
        }

        userBasket.setBooks(books);

        return basketRepository.save(userBasket);
    }

    @MutationMapping
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Basket removeBookFromCart(@Argument Long bookId, @Argument Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Basket userBasket = user.getBasket();

        if (userBasket == null) {
            throw new IllegalArgumentException("Basket not found");
        }

        List<BookBasket> books = userBasket.getBooks();
        boolean bookInBasket = false;
        for (BookBasket book : books) {
            if (book.getBook().getId().equals(bookId)) {
                int bookQuantityInStock = book.getBook().getQuantity();
                int bookQuantityInBasket = book.getQuantity();
                book.getBook().setQuantity(bookQuantityInStock + bookQuantityInBasket);
                books.remove(book);
                bookInBasket = true;
                break;
            }
        }
        if (!bookInBasket) {
            throw new IllegalArgumentException("Book not found in basket");
        }

        userBasket.setBooks(books);

        bookBasketRepository.deleteByBookIdAndBasketId(bookId, userBasket.getId());

        return basketRepository.save(userBasket);
    }

    @QueryMapping
    public Basket basketByUserId(@Argument Long userId) {
        return basketRepository.findByUserId(userId).orElse(null);
    }

//    @SchemaMapping
//    public Author author(BookService bookService) {
//        return AuthorRepository.findById(bookService.authorId());
//    }
}