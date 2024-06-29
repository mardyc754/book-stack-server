package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.UploadedImage;
import com.bookstack.bookstack.repositories.BookRepository;
import com.bookstack.bookstack.repositories.UploadedImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public UploadedImage uploadPhoto(@RequestParam("image") MultipartFile file, @RequestParam("bookId") Long bookId) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = storageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        UploadedImage photo = new UploadedImage();
        photo.setFilename(filename);
        photo.setType(file.getContentType());
        photo.setContent(file.getBytes());

        System.out.println(photo);


        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book for given cover image not found");
        }
        book.setImage(photo);
        bookRepository.save(book);

        return uploadedImageRepository.save(photo);

//        photo.setFilename("/photos/files/" + filename);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public Resource getFile(@PathVariable String filename) throws IOException {
        Path filePath = storageLocation.resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }

    @GetMapping("/")
    public List<UploadedImage> allPhotos() {
        return uploadedImageRepository.findAll();
    }
}