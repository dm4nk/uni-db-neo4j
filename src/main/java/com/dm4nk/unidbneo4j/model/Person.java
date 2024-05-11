package com.dm4nk.unidbneo4j.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.UUID;

@Node
@Data
@Builder
@ToString(exclude = {"teammates"})
public class Person {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @Relationship(type = "TEAMMATE")
    private Set<Person> teammates;
}
