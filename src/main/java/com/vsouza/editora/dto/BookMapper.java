package com.vsouza.editora.dto;

import com.vsouza.editora.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(BookRequest dto);
    BookRequest toBookDTO(Book book);

    @Mapping(target = "author", source = "book.author.fullName")
    BookResponse toBookResponse(Book book);
}
