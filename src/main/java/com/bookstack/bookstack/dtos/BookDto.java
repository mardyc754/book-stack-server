package com.bookstack.bookstack.dtos;

import com.bookstack.bookstack.models.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BookDto {

    @Getter
    private Long id;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private double price;

    @Setter
    @Getter
    private LocalDate publicationDate;

    @Setter
    @Getter
    private int pageCount;

    @Setter
    @Getter
    private UploadImageGraphqlDto image;

    @Setter
    @Getter
    private String ISBN;

    @Setter
    @Getter
    private String description;


    @Setter
    @Getter
    private Publisher publisher;


    @Setter
    @Getter
    private List<Author> authors;

    @Setter
    @Getter
    private List<Category> categories;

    @Setter
    @Getter
    private int quantity;


    private List<BookBasket> bookBasket = new ArrayList<>();

    public static List<BookDto> fromBooks(List<Book> books) {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }


    public BookDto() {
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.publicationDate = book.getPublicationDate();
        this.pageCount = book.getPageCount();
        this.ISBN = book.getISBN();
        this.description = book.getDescription();
        this.publisher = book.getPublisher();
        this.authors = book.getAuthors();
        this.categories = book.getCategories();
        this.quantity = book.getQuantity();

        if (book.getImage() == null) {
            return;
        }

        this.image = new UploadImageGraphqlDto();
        this.image.setBookId(book.getId());
        this.image.setFilename(book.getImage().getFilename());
        this.image.setType(book.getImage().getType());
        this.image.setContent(Base64.getEncoder().encodeToString(book.getImage().getContent()));
    }
}
