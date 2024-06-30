package com.bookstack.bookstack.dtos;

import com.bookstack.bookstack.models.Book;
import com.bookstack.bookstack.models.BoughtBook;
import com.bookstack.bookstack.models.BoughtBookId;
import com.bookstack.bookstack.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoughtBookDto {
    private BoughtBookId id;


    private BookDto book;


    private User user;


    private int quantity;

    public static List<BoughtBookDto> fromBoughtBooks(List<BoughtBook> boughtBooks) {
        return boughtBooks.stream().map(BoughtBookDto::new).collect(Collectors.toList());
    }

    public BoughtBookDto() {
    }

    public BoughtBookDto(BoughtBook boughtBook) {
        this.id = boughtBook.getId();
        this.book = new BookDto(boughtBook.getBook());
        this.user = boughtBook.getUser();
        this.quantity = boughtBook.getQuantity();
    }
}
