package com.vsouza.editora.services;

import com.vsouza.editora.controllers.exceptions.ResourceNotFoundException;
import com.vsouza.editora.dto.AuthorDTO;
import com.vsouza.editora.dto.AuthorMapper;
import com.vsouza.editora.entities.Author;
import com.vsouza.editora.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public AuthorDTO save(AuthorDTO authorRequest) {
        Author author = authorMapper.toAuthorEntity(authorRequest);
        authorRepository.save(author);
        return authorMapper.toAuthorDTO(author);
    }

    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toAuthorDTO).toList();
    }

    public AuthorDTO findOne(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(authorMapper::toAuthorDTO).orElseThrow(() -> new ResourceNotFoundException("Author não encontrado"));
    }

    public void update(Long id, AuthorDTO authorRequest) {
        log.info("Updating author with id {}", id);
        AuthorDTO authorResponse = this.findOne(id);


        if(authorResponse != null) {
            log.info("Author found? {} ", authorResponse);
            Author author = authorMapper.toAuthorEntity(authorRequest);
            author.setId(authorResponse.getId());
            authorRepository.save(author);
        }
    }
}
