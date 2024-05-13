package com.dm4nk.unidbneo4j.repository.client;

import com.dm4nk.unidbneo4j.model.Author;
import com.dm4nk.unidbneo4j.model.Book;
import lombok.AllArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dm4nk.unidbneo4j.constants.Queries.GET_ALL_BOOKS_WITH_AUTHORS;
import static com.dm4nk.unidbneo4j.constants.Queries.GET_BOOK_BY_AUTHOR_WITH_AUTHOR;
import static com.dm4nk.unidbneo4j.constants.Queries.GET_BOOK_BY_NAME_WITH_AUTHOR;

@Repository
@AllArgsConstructor
public class ClientBookRepositoryImpl implements ClientBookRepository {
    private final Neo4jClient client;

    @Override
    public List<Book> getAllBooks() {
        return client.query(GET_ALL_BOOKS_WITH_AUTHORS)
                .fetchAs(Book.class).mappedBy((TypeSystem t, Record record) -> {
                    Book book = Book.builder()
                            .id(UUID.fromString(record.get("bookId").toString().replace("\"", "")))
                            .title(record.get("title").asString())
                            .year(record.get("year").asInt())
                            .build();
                    Author author = Author.builder()
                            .id(UUID.fromString(record.get("authorId").toString().replace("\"", "")))
                            .name(record.get("name").asString())
                            .build();
                    book.setAuthor(author);
                    return book;
                })
                .all()
                .stream()
                .toList();
    }

    @Override
    public Optional<Book> findByName(String name) {
        return client.query(GET_BOOK_BY_NAME_WITH_AUTHOR)
                .bind(name).to("title")
                .fetchAs(Book.class).mappedBy((TypeSystem t, Record record) -> {
                    Book book = Book.builder()
                            .id(UUID.fromString(record.get("bookId").toString().replace("\"", "")))
                            .title(record.get("title").asString())
                            .year(record.get("year").asInt())
                            .build();
                    Author author = Author.builder()
                            .id(UUID.fromString(record.get("authorId").toString().replace("\"", "")))
                            .name(record.get("name").asString())
                            .build();
                    book.setAuthor(author);
                    return book;
                })
                .one();
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        return client.query(GET_BOOK_BY_AUTHOR_WITH_AUTHOR)
                .bind(authorName).to("author")
                .fetchAs(Book.class).mappedBy((TypeSystem t, Record record) -> {
                    Book book = Book.builder()
                            .id(UUID.fromString(record.get("bookId").toString().replace("\"", "")))
                            .title(record.get("title").asString())
                            .year(record.get("year").asInt())
                            .build();
                    Author author = Author.builder()
                            .id(UUID.fromString(record.get("authorId").toString().replace("\"", "")))
                            .name(record.get("name").asString())
                            .build();
                    book.setAuthor(author);
                    return book;
                })
                .all()
                .stream()
                .toList();
    }
}
