package com.bookstack.bookstack.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadImageGraphqlDto {


    private Long bookId;
    private String filename;
    private String type;
    private String content;

    public UploadImageGraphqlDto() {
    }

    public UploadImageGraphqlDto(Long bookId, String filename, String type, String content) {
        this.bookId = bookId;
        this.filename = filename;
        this.type = type;
        this.content = content;
    }
}
