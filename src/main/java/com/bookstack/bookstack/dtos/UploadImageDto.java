package com.bookstack.bookstack.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadImageDto {


    private Long bookId;
    private String filename;
    private String type;
    private byte[] content;

    public UploadImageDto() {
    }

    public UploadImageDto(Long bookId, String filename, String type, byte[] content) {
        this.bookId = bookId;
        this.filename = filename;
        this.type = type;
        this.content = content;
    }
}
