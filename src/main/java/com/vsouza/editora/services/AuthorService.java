package com.vsouza.editora.services;

import com.vsouza.editora.controllers.exceptions.DatabaseException;
import com.vsouza.editora.controllers.exceptions.ResourceNotFoundException;
import com.vsouza.editora.dto.AuthorDTO;
import com.vsouza.editora.dto.AuthorMapper;
import com.vsouza.editora.entities.Author;
import com.vsouza.editora.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        emailAlreadyExists(authorRequest.getEmail());
        Author author = authorMapper.toAuthorEntity(authorRequest);
        authorRepository.save(author);
        return authorMapper.toAuthorDTO(author);
    }

    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toAuthorDTO).toList();
    }

    public Author findById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new ResourceNotFoundException("Author não encontrado"));
    }

    public AuthorDTO findOne(Long id) {
        Author author = this.findById(id);
        return authorMapper.toAuthorDTO(author);
    }

    public void update(Long id, AuthorDTO authorRequest) {
        log.info("Updating author with id {}", id);
        AuthorDTO authorResponse = this.findOne(id);


        if(authorResponse != null) {
            Author author = authorMapper.toAuthorEntity(authorRequest);
            if(!author.getEmail().equals(authorRequest.getEmail())) {
                emailAlreadyExists(authorRequest.getEmail());
            }
            author.setId(authorResponse.getId());
            authorRepository.save(author);
        }
    }

    public void deleteAuthor(Long id) {
        log.info("Deleting author with id {}", id);
        try {
            authorRepository.deleteById(id);
        }catch (EntityNotFoundException e) {
            log.info("Author not found");
            throw new ResourceNotFoundException("Author not found");
        }catch (DataIntegrityViolationException e){
            log.info("Integrity violation");
            throw  new DatabaseException("Integrity violation");
        }

    }

    private void emailAlreadyExists(String email) {
        if (authorRepository.existsByEmail(email)) {
            log.info("Email already exists");
            throw new DatabaseException("Email already exists");
        }
    }
}
