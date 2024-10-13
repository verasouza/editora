package com.vsouza.editora.controllers;

import com.vsouza.editora.dto.AuthorDTO;
import com.vsouza.editora.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.save(authorDTO);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<AuthorDTO> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
       authorService.update(id, authorDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.findOne(id);
        return ResponseEntity.ok(authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
