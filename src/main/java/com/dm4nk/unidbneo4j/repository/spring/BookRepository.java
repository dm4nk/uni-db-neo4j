package com.dm4nk.unidbneo4j.repository.spring;

import com.dm4nk.unidbneo4j.model.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dm4nk.unidbneo4j.constants.Queries.GET_BOOK_BY_NAME;
import static com.dm4nk.unidbneo4j.constants.Queries.DELETE_BOOK_ONLY_BY_TITLE;
import static com.dm4nk.unidbneo4j.constants.Queries.GET_BOOK_YEAR;

public interface BookRepository extends Neo4jRepository<Book, UUID> {

    @Query(GET_BOOK_BY_NAME)
    Optional<Book> findOneByTitle(@Param("title") String title);

    @Query(GET_BOOK_YEAR)
    List<Book> findAllByYear(@Param("year") Integer year);

    @Query(value = DELETE_BOOK_ONLY_BY_TITLE, delete = true)
    void deleteBookByTitle(@Param("title") String title);
}
