package com.bookstack.bookstack.services;

import com.bookstack.bookstack.dtos.BasketDto;
import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.repositories.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BasketService {
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;
    private final BookBasketRepository bookBasketRepository;

    private final UserRepository userRepository;

    private final BoughtBookRepository boughtBookRepository;

    public BasketService(BookRepository bookRepository, BasketRepository basketRepository, UserRepository userRepository, BookBasketRepository bookBasketRepository,
                            BoughtBookRepository boughtBookRepository
    ) {
        this.bookRepository = bookRepository;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.bookBasketRepository = bookBasketRepository;
        this.boughtBookRepository = boughtBookRepository;
    }

    public BasketDto addBookToCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
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
            Basket newBasket = new Basket();
            newBasket.setUser(user);
            Basket createdBasket = basketRepository.save(newBasket);

            BookBasketId id = new BookBasketId();
            id.setBookId(bookId);
            id.setBasketId(createdBasket.getId());
            BookBasket bookBasket = new BookBasket(bookAddedToBasket, createdBasket, quantity);
            bookBasket.setId(id);
            bookBasketRepository.save(bookBasket);
            createdBasket.setBooks(new ArrayList<>(List.of(bookBasketRepository.save(bookBasket))));
            return new BasketDto(basketRepository.save(createdBasket));
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

        return new BasketDto(basketRepository.save(userBasket));
    }



    public BasketDto changeBookQuantityInCart(@Argument Long bookId, @Argument Long userId, @Argument Integer quantity) {
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
                book.getBook().setQuantity(bookQuantityInStock + (oldQuantity - newQuantity));
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

        return new BasketDto(basketRepository.save(userBasket));
    }

    public BasketDto removeBookFromCart(@Argument Long bookId, @Argument Long userId) {
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

        return new BasketDto(basketRepository.save(userBasket));
    }


    public BasketDto basketByUserId(@Argument Long userId) {
        Basket userBasket = basketRepository.findByUserId(userId).orElse(null);
        return userBasket != null ? new BasketDto(userBasket) : null;
    }


    public BasketDto buyBooks(@Argument Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Basket basket = user.getBasket();

        List<BookBasket> books = basket.getBooks();

        for (BookBasket bookBasket : books) {
            Book book = bookBasket.getBook();


            BoughtBook boughtByUser = user.getBoughtBooks().stream()
                    .filter(boughtBook -> boughtBook.getBook().getId().equals(book.getId()))
                    .findFirst()
                    .orElse(null);

            if (boughtByUser == null) {
                BoughtBookId id = new BoughtBookId();
                id.setBookId(book.getId());
                id.setUserId(user.getId());

                BoughtBook boughtBook = new BoughtBook(book, user, bookBasket.getQuantity());
                boughtBook.setId(id);

                boughtBookRepository.save(boughtBook);
            } else {
                boughtByUser.setQuantity(boughtByUser.getQuantity() + bookBasket.getQuantity());
                boughtBookRepository.save(boughtByUser);
            }
            basket.setBooks(null);
            bookBasketRepository.deleteByBookIdAndBasketId(book.getId(), basket.getId());
        }

        return new BasketDto(basketRepository.save(basket));
    }
}
