package com.dm4nk.unidbneo4j.bootstrap;

import com.dm4nk.unidbneo4j.model.Author;
import com.dm4nk.unidbneo4j.model.Book;
import com.dm4nk.unidbneo4j.repository.AuthorRepository;
import com.dm4nk.unidbneo4j.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

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


    }
}
