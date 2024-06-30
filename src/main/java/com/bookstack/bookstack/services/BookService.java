package com.bookstack.bookstack.services;

import com.bookstack.bookstack.dtos.BookDto;
import com.bookstack.bookstack.dtos.BoughtBookDto;
import com.bookstack.bookstack.models.*;
import com.bookstack.bookstack.repositories.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BoughtBookRepository boughtBookRepository;

    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;

    private final UploadedImageRepository uploadedImageRepository;

    public BookService(BookRepository bookRepository,
                       BoughtBookRepository boughtBookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       CategoryRepository categoryRepository,
                       UploadedImageRepository uploadedImageRepository
    ) {
        this.bookRepository = bookRepository;
        this.boughtBookRepository = boughtBookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.uploadedImageRepository = uploadedImageRepository;
    }


    public BookDto bookById(Long id) {
//        return bookRepository.findById(id).orElse(null);
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        return new BookDto(book);
    }

    public List<BookDto> allBooks(Optional<Integer> minQuantity) {

        return minQuantity
                .map(integer -> BookDto.fromBooks(bookRepository.findAllByQuantityGreaterThan(integer)))
                .orElseGet(() -> BookDto.fromBooks(bookRepository.findAll()));

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
//    ) throws IOException {
    ) {
        System.out.println("title: " + title);
        System.out.println("price: " + price);
        System.out.println("publicationDate: " + publicationDate);
        System.out.println("pageCount: " + pageCount);
        System.out.println("ISBN: " + ISBN);
        System.out.println("description: " + description);
        System.out.println("quantity: " + quantity);
        System.out.println("publisherId: " + publisherId);
        System.out.println("authorIds: " + authorIds);
        System.out.println("categoryIds: " + categoryIds);

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

        UploadedImage uploadedImage = null;

//        if (image != null) {
//                String originalFileName = image.getOriginalFilename();
//                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//            try {
//                uploadedImage = new UploadedImage(fileName, image.getContentType(), image.getBytes());
//    //            Path path = Paths.get("src/main/resources/static/images/" + fileName);
//    //            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//                uploadedImageRepository.save(uploadedImage);
//            }
//            catch (IOException e) {
//                throw new IllegalArgumentException("Image upload failed");
//            }
//        }
        Book book = new Book(
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
        );

//        if (uploadedImage != null) {
//            book.setImage(uploadedImage);
//        }

        return new BookDto(bookRepository.save(book));
    }
}
