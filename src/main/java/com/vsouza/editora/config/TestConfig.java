package com.vsouza.editora.config;

import com.vsouza.editora.entities.Author;
import com.vsouza.editora.entities.Book;
import com.vsouza.editora.repositories.AuthorRepository;
import com.vsouza.editora.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public void run(String... args) throws Exception {

        Author author = new Author();
        author.setId(1L);
        author.setFullName("Agatha Christie");
        author.setEmail("agatha@vsouza.com");
        authorRepository.save(author);

        Author authorDois = new Author();
        authorDois.setId(2L);
        authorDois.setFullName("Harlan Coben");
        authorDois.setEmail("harlan@vsouza.com");
        authorRepository.save(authorDois);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Convite para um homicidio");
        book.setPages(100);
        book.setIsbn("987-741-485-125-1");
        book.setGenre("Policial");
        book.setAuthor(author);
        bookRepository.save(book);

        Book bookDois = new Book();
        bookDois.setId(2L);
        bookDois.setTitle("A noite das bruxas");
        bookDois.setPages(200);
        bookDois.setIsbn("947-741-485-258-3");
        bookDois.setGenre("Policial");
        bookDois.setAuthor(author);
        bookRepository.save(bookDois);

    }
}
