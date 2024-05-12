package com.dm4nk.unidbneo4j.bootstrap;

import com.dm4nk.unidbneo4j.model.Author;
import com.dm4nk.unidbneo4j.model.Book;
import com.dm4nk.unidbneo4j.repository.spring.AuthorRepository;
import com.dm4nk.unidbneo4j.repository.spring.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final Neo4jClient client;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        authorRepository.deleteAll();
        bookRepository.deleteAll();

        Author martin = Author.builder()
                .name("Martin")
                .build();

        Author karnegi = Author.builder()
                .name("Karnegi")
                .build();

        Book book1 = Book.builder()
                .title("Clear Code")
                .author(martin)
                .year(1999)
                .build();

        Book book2 = Book.builder()
                .title("Clear Architecture")
                .author(martin)
                .year(2000)
                .build();

        Book book3 = Book.builder()
                .title("Relationships")
                .author(karnegi)
                .year(1999)
                .build();
        martin.setBooks(List.of(book1, book2));
        karnegi.setBooks(List.of(book3));

        bookRepository.saveAll(List.of(book1, book2, book3));

        Book bookByTitle = bookRepository.findOneByTitle("Clear Code").orElse(null);
        log.debug("Found book by title: {}", bookByTitle);

        List<Book> allByYear = bookRepository.findAllByYear(1999);
        log.debug("Found books by year: {}", allByYear);

        // client example
        Optional<Book> one = client.query("MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.title = $title RETURN b.id as bookId, b.title as title, b.year as year, a.id as authorId, a.name as name")
                .bind("Clear Code").to("title")
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

        log.debug("Found one: {}", one.orElse(null));
    }
}
