package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.dtos.UploadImageDto;
import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.UploadedImage;
import com.bookstack.bookstack.repositories.BookRepository;
import com.bookstack.bookstack.repositories.UploadedImageRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/images")
public class UploadedImageController {

    private UploadedImageRepository uploadedImageRepository;

    private BookRepository bookRepository;

    private final Path storageLocation = Paths.get("uploads");


    public UploadedImageController(UploadedImageRepository uploadedImageRepository, BookRepository bookRepository) {
        this.uploadedImageRepository = uploadedImageRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UploadImageDto uploadImage(@RequestPart("image") MultipartFile file, @RequestPart("bookId") Long bookId) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        UploadedImage photo = new UploadedImage();
        photo.setFilename(filename);
        photo.setType(file.getContentType());
        photo.setContent(file.getBytes());

        System.out.println(photo.getFilename());
        System.out.println("bookId: " + bookId);

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book for given cover image not found");
        }

        photo.setBook(book);
        uploadedImageRepository.save(photo);

        UploadImageDto uploadImageDto = new UploadImageDto();
        uploadImageDto.setBookId(bookId);
        uploadImageDto.setFilename(filename);
        uploadImageDto.setType(file.getContentType());
        uploadImageDto.setContent(file.getBytes());
        
        return uploadImageDto;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UploadImageDto getImageById(@PathVariable String id) throws IOException {
        UploadedImage photo = uploadedImageRepository.findById(Long.parseLong(id)).orElse(null);
        if (photo == null) {
            throw new IllegalArgumentException("Image not found");
        }

        return new UploadImageDto(photo.getBook().getId(), photo.getFilename(), photo.getType(), photo.getContent());
    }

    @GetMapping("/")
    public List<UploadedImage> allPhotos() {
        return uploadedImageRepository.findAll();
    }
}