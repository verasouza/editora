package com.vsouza.editora;

import com.vsouza.editora.entities.Author;
import com.vsouza.editora.entities.Book;
import com.vsouza.editora.repositories.AuthorRepository;
import com.vsouza.editora.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BookTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveBook() {
        int actualBooksSize = bookRepository.findAll().size();
        log.info("Count {}", actualBooksSize);
        Book book = getBook();
        bookRepository.save(book);
        Assertions.assertEquals(actualBooksSize + 1, bookRepository.findAll().size());
    }

    @Test
    void testUpdateBook() {
        Book book = bookRepository.findAll().get(0);
        int bookPages = book.getPages();
        String oldTitle = book.getTitle();
        book.setPages(book.getPages() + 1);
        book.setTitle("New title");
        bookRepository.save(book);
        Assertions.assertEquals(bookPages + 1, book.getPages());
        Assertions.assertNotEquals(oldTitle, book.getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = bookRepository.findAll().get(0);
        Long bookId = book.getId();
        bookRepository.deleteById(bookId);
        Assertions.assertNull(bookRepository.findById(bookId));
    }

    //return book objetc
    Book getBook() {
        Author author = new Author();
        author.setFullName("Vera Souza");
        author.setEmail("vera@vsouza.com");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Como escrever testes");
        book.setPages(200);
        book.setGenre("Tecnologia");
        book.setPublisher("Globo livros");
        book.setAuthor(author);
        book.setIsbn("987-741-485-125-1");
        return book;
    }
}
