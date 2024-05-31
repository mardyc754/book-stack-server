INSERT INTO "publisher" (name) VALUES ('Scribner'),('Secker & Warburg'),('Houghton Mifflin'),('Doubleday');

INSERT INTO "book" (title, publication_date, page_count, ISBN, description, price, publisher_id) VALUES ('The Great Gatsby',
                                                                                                         '1925-04-10', 180, '9780743273565',
                                                                                                         'The Great Gatsby is a novel written by American authorService F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922.',
                                                                                                         7.99, 1),
                                                                                                        ('To Kill a Mockingbird',
                                                                                                         '1960-07-11', 281,
                                                                                                         '9780061120084',
                                                                                                         'To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, winning the Pulitzer Prize, and has become a classic of modern American literature.',
                                                                                                         9.99, 1),
                                                                                                        ('1984',
                                                                                                         '1949-06-08', 328,
                                                                                                         '9780452284234',
                                                                                                         '1984 is a dystopian novella by George Orwell published in 1949, which follows the life of Winston Smith, a low ranking member of the Party, who is frustrated by the omnipresent surveillance of the Party, and its ominous ruler Big Brother.',
                                                                                                         6.99, 2),
                                                                                                        ('The Catcher in the Rye',
                                                                                                         '1951-07-16', 234,
                                                                                                         '9780316769487',
                                                                                                         'The Catcher in the Rye is a novel by J. D. Salinger, partially published in serial form in 1945–1946 and as a novel in 1951.',
                                                                                                         8.99, 2),
                                                                                                        ('The Hobbit',
                                                                                                         '1937-09-21', 310,
                                                                                                         '9780618002214',
                                                                                                         'The Hobbit, or There and Back Again is a childrens fantasy novel by English authorService J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.',
                                                                                                         10.99, 3),
                                                                                                        ('The Lord of the Rings',
                                                                                                         '1954-07-29', 1178,
                                                                                                         '9780395193957',
                                                                                                         'The Lord of the Rings is an epic high fantasy novel by the English authorService and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkiens 1937 childrens bookService The Hobbit, but eventually developed into a much larger work.',
                                                                                                         19.99, 3),
                                                                                                        ('The Da Vinci Code',
                                                                                                         '2003-03-18', 454,
                                                                                                         '9780385504205',
                                                                                                         'The Da Vinci Code is a 2003 mystery thriller novel by Dan Brown. It follows symbologist Robert Langdon and cryptologist Sophie Neveu',
                                                                                                         14.99, 4),
                                                                                                        ('Angels & Demons',
                                                                                                         '2000-05-01', 713,
                                                                                                         '9780671027357',
                                                                                                         'Angels & Demons is a 2000 bestselling mystery-thriller novel written by American authorService Dan Brown and published by Pocket Books and then by Corgi Books.',
                                                                                                         12.99, 4);

INSERT INTO "author" ("first_name", "last_name") VALUES ('F. Scott','Fitzgerald'),('Harper','Lee'),('George','Orwell'),('J. D.','Salinger'),('J. R. R.','Tolkien'),('Dan','Brown');
INSERT INTO "book_author" ("book_id", "author_id") VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,5),(7,6),(8,6);

INSERT INTO "category" (name) VALUES ('Fiction'),('Non-Fiction'),('Fantasy'),('Mystery'),('Thriller');
INSERT INTO "book_category" ("book_id", "category_id") VALUES (1,1),(2,1),(3,1),(4,1),(5,3),(6,3),(7,4),(7,5),(8,4),(8,5);
