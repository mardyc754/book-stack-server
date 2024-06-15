package com.bookstack.bookstack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Basket")
public class Basket implements Serializable {

        @Id
        @Column(name = "id",  columnDefinition = "serial")
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        @Setter
        private User user;

        @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
        @Setter
        private List<BookBasket> books;

        public Basket() {
        }

        public Basket(User user, List<BookBasket> book) {
            this.user = user;
            this.books = book;
        }

        public Basket(User user, BookBasket book) {
                this.user = user;
                this.books = new ArrayList<>();
                this.books.add(book);
        }
}
