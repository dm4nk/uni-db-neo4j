package com.dm4nk.unidbneo4j.repository.client;

import com.dm4nk.unidbneo4j.model.Book;

import java.util.List;
import java.util.Optional;

public interface ClientBookRepository {
    List<Book> getAllBooks();
    Optional<Book> findByName(String name);
    List<Book> findBooksByAuthor(String authorName);
}
