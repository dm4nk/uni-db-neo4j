package com.dm4nk.unidbneo4j.repository.spring;

import com.dm4nk.unidbneo4j.model.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends Neo4jRepository<Book, UUID> {

    @Query("MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.title = $title RETURN b")
    Optional<Book> findOneByTitle(@Param("title") String title);

    @Query("MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.year = $year RETURN b")
    List<Book> findAllByYear(@Param("year") Integer year);
}
