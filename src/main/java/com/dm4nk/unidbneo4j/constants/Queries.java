package com.dm4nk.unidbneo4j.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Queries {
    public static final String GET_ALL_BOOKS_WITH_AUTHORS = "MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) RETURN b.id as bookId, b.title as title, b.year as year, a.id as authorId, a.name as name";
    public static final String GET_BOOK_BY_NAME_WITH_AUTHOR = "MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.title = $title RETURN b.id as bookId, b.title as title, b.year as year, a.id as authorId, a.name as name";
    public static final String GET_BOOK_BY_AUTHOR_WITH_AUTHOR = "MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE a.name = $author RETURN b.id as bookId, b.title as title, b.year as year, a.id as authorId, a.name as name";
    public static final String GET_BOOK_BY_NAME = "MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.title = $title RETURN b";
    public static final String GET_BOOK_YEAR = "MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE b.year = $year RETURN b";
    public static final String DELETE_BOOK_ONLY_BY_TITLE = "MATCH (b:Book) where b.title = $title DETACH DELETE b ";


}
