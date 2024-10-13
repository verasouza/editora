package com.vsouza.editora.services;

import com.vsouza.editora.controllers.exceptions.ResourceNotFoundException;
import com.vsouza.editora.dto.BookRequest;
import com.vsouza.editora.dto.BookMapper;
import com.vsouza.editora.dto.BookResponse;
import com.vsouza.editora.entities.Author;
import com.vsouza.editora.entities.Book;
import com.vsouza.editora.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorService authorService;

    public BookResponse save(BookRequest bookRequest) {
        Author author = authorService.findById(bookRequest.getAuthorId());
        Book book = bookMapper.toBook(bookRequest);
        book.setAuthor(author);
        book = bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::toBookResponse).toList();
    }

    public BookResponse findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(value -> bookMapper.toBookResponse(value)).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public void update(Long id, BookRequest bookRequest) {
        log.info("BOOK_SERVICE - updating book {} ", id);
        BookResponse book = this.findById(id);
        Author author = authorService.findById(bookRequest.getAuthorId());

        if(book != null) {
            Book bookEntity = bookMapper.toBook(bookRequest);
            bookEntity.setAuthor(author);
            bookRepository.save(bookEntity);
        }
    }

    public void delete(Long id) {
        log.info("BOOK_SERVICE - delete book {} ", id);
        bookRepository.deleteById(id);
    }
}
