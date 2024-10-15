package com.vsouza.editora.config;

import com.vsouza.editora.config.security.SecurityConfig;
import com.vsouza.editora.entities.Author;
import com.vsouza.editora.entities.Book;
import com.vsouza.editora.entities.User;
import com.vsouza.editora.repositories.AuthorRepository;
import com.vsouza.editora.repositories.BookRepository;
import com.vsouza.editora.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;


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
        book.setPages(304);
        book.setIsbn("978-8525425690");
        book.setGenre("Policial");
        book.setAuthor(author);
        book.setPublishYear(1950);
        book.setQuantity(100);
        book.setPublisher("L&PM");
        bookRepository.save(book);

        Book bookDois = new Book();
        bookDois.setId(2L);
        bookDois.setTitle("A Casa do Penhasco ");
        bookDois.setPages(224);
        bookDois.setIsbn("978-8525420985");
        bookDois.setGenre("Policial");
        bookDois.setAuthor(author);
        book.setPublishYear(1932);
        book.setQuantity(150);
        book.setPublisher("L&PM");
        bookRepository.save(bookDois);

        User user = new User();
        user.setId(1L);
        user.setEmail("admin@vsouza.com");
        user.setUsername("admin");
        user.setPassword(securityConfig.passwordEncoder().encode("admin"));
        user.setEnabled(true);
        userRepository.save(user);

    }
}
