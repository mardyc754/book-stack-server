package com.bookstack.bookstack.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class BookBasketId  implements Serializable {

    private Long bookId;
    private Long basketId;
}
