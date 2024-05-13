CREATE TABLE "Publisher"(
                            "id" BIGINT NOT NULL,
                            "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "Publisher" ADD PRIMARY KEY("id");
ALTER TABLE
    "Publisher" ADD CONSTRAINT "publisher_name_unique" UNIQUE("name");
CREATE TABLE "Category"(
                           "id" BIGINT NOT NULL,
                           "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "Category" ADD PRIMARY KEY("id");
CREATE TABLE "Book_Author"(
                              "bookId" BIGINT NOT NULL,
                              "authorId" BIGINT NOT NULL
);
CREATE TABLE "Author"(
                         "id" BIGINT NOT NULL,
                         "firstName" VARCHAR(255) NOT NULL,
                         "lastName" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "Author" ADD PRIMARY KEY("id");
CREATE TABLE "Book_Category"(
                                "bookId" BIGINT NOT NULL,
                                "categoryId" BIGINT NOT NULL
);
CREATE TABLE "Book"(
                       "id" BIGINT NOT NULL,
                       "title" VARCHAR(255) NOT NULL,
                       "price" DECIMAL(8, 2) NOT NULL,
                       "publicationDate" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
                       "pageCount" BIGINT NOT NULL,
                       "ISBN" VARCHAR(255) NOT NULL,
                       "description" VARCHAR(255),
                       "publisher" BIGINT NOT NULL
);
ALTER TABLE
    "Book" ADD PRIMARY KEY("id");
ALTER TABLE
    "Book" ADD CONSTRAINT "book_isbn_unique" UNIQUE("ISBN");
ALTER TABLE
    "Book_Category" ADD CONSTRAINT "book_category_bookid_foreign" FOREIGN KEY("bookId") REFERENCES "Book"("id");
ALTER TABLE
    "Book" ADD CONSTRAINT "book_publisher_foreign" FOREIGN KEY("publisher") REFERENCES "Publisher"("id");
ALTER TABLE
    "Book_Category" ADD CONSTRAINT "book_category_categoryid_foreign" FOREIGN KEY("categoryId") REFERENCES "Category"("id");
ALTER TABLE
    "Book_Author" ADD CONSTRAINT "book_author_bookid_foreign" FOREIGN KEY("bookId") REFERENCES "Book"("id");
ALTER TABLE
    "Book_Author" ADD CONSTRAINT "book_author_authorid_foreign" FOREIGN KEY("authorId") REFERENCES "Author"("id");

CREATE SEQUENCE publisher_id_seq;
ALTER SEQUENCE publisher_id_seq OWNED BY "Publisher"."id";
ALTER TABLE "Publisher" ALTER COLUMN id SET DEFAULT nextval('publisher_id_seq');

CREATE SEQUENCE book_id_seq;
ALTER SEQUENCE book_id_seq OWNED BY "Book"."id";
ALTER TABLE "Book" ALTER COLUMN id SET DEFAULT nextval('book_id_seq');

CREATE SEQUENCE category_id_seq;
ALTER SEQUENCE category_id_seq OWNED BY "Category"."id";
ALTER TABLE "Category" ALTER COLUMN id SET DEFAULT nextval('category_id_seq');

CREATE SEQUENCE author_id_seq;
ALTER SEQUENCE author_id_seq OWNED BY "Author"."id";
ALTER TABLE "Author" ALTER COLUMN id SET DEFAULT nextval('author_id_seq');