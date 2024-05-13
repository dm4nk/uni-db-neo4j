package com.dm4nk.unidbneo4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.UUID;

@Node("Author")
@Data
@Builder
@ToString(exclude = {"books"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.INCOMING)
    private List<Book> books;
}
