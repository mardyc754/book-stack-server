package com.bookstack.bookstack;

import com.bookstack.bookstack.controllers.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(BookController.class)
public class BookServiceControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstBook() {
        this.graphQlTester
                .documentName("bookDetails")
                .variable("id", "bookService-1")
                .execute()
                .path("bookById")
                .matchesJson("""
                    {
                        "id": "bookService-1",
                        "name": "Effective Java",
                        "pageCount": 416,
                        "authorService": {
                          "firstName": "Joshua",
                          "lastName": "Bloch"
                        }
                    }
                """);
    }
}