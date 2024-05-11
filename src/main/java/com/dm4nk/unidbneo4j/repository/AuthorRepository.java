package com.dm4nk.unidbneo4j.repository;

import com.dm4nk.unidbneo4j.model.Author;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends Neo4jRepository<Author, UUID> {

    @Query("MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE ф.name = $name RETURN b, a.name")
    Optional<Author> findAuthorByName(String name);
}
