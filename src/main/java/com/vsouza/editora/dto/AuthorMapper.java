package com.vsouza.editora.dto;

import com.vsouza.editora.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toAuthorDTO(Author author);
    Author toAuthorEntity(AuthorDTO authorDTO);
}
