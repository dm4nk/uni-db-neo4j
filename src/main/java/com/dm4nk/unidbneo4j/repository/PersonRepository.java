package com.dm4nk.unidbneo4j.repository;

import com.dm4nk.unidbneo4j.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends Neo4jRepository<Person, UUID> {
    Person findByName(String name);
    List<Person> findByTeammatesName(String name);
}
