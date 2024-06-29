package com.bookstack.bookstack.repositories;

import com.bookstack.bookstack.models.UploadedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {
}
