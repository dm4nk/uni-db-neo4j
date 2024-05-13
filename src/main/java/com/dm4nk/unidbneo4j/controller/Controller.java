package com.dm4nk.unidbneo4j.controller;

import com.dm4nk.unidbneo4j.model.Book;
import com.dm4nk.unidbneo4j.repository.client.ClientBookRepository;
import com.dm4nk.unidbneo4j.repository.spring.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class Controller {
    private final BookRepository bookRepository;
    private final ClientBookRepository clientBookRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(clientBookRepository.getAllBooks());
    }

    @GetMapping
    public ResponseEntity<Book> get(@RequestParam String name) {
        return ResponseEntity.ok(clientBookRepository.findByName(name).orElse(null));
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(clientBookRepository.findBooksByAuthor(author));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        if (book.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookRepository.save(book));
    }

    @PatchMapping
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if (book.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookRepository.save(book));
    }

    @DeleteMapping
    public ResponseEntity<Void> update(@RequestParam String name) {
        bookRepository.deleteBookByTitle(name);
        return ResponseEntity.ok().build();
    }
}
