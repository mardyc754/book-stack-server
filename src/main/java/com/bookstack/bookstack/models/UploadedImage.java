package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Image")
public class UploadedImage {

    @Id
    @Column(name = "id",  columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;

    @Setter
    @Getter
    private String filename;

    @Setter
    @Getter
    private String type;

    @Setter
    @Getter
    private byte[] content;

    @OneToOne
    @MapsId
    @Getter
    @Setter
    @JoinColumn(name = "id")
    private Book book;

    public UploadedImage() {
    }

    public UploadedImage(String filename, String type, byte[] content) {
        this.filename = filename;
        this.type = type;
        this.content = content;
    }
}
