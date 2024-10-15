package com.vsouza.editora.controllers;

import com.vsouza.editora.dto.BookRequest;
import com.vsouza.editora.dto.BookResponse;
import com.vsouza.editora.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse book = bookService.save(bookRequest);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookRequest> update(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        bookService.update(id, bookRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        BookResponse bookDTO = bookService.findById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.delete(id);
    }
}
