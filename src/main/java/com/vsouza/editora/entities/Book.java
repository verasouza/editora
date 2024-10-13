package com.vsouza.editora.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "tb_books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Preencher o título do livro")
    private String title;
    private String publisher;
    @NotEmpty(message = "Preencher o ISBN do livro")
    private String isbn;
    @NotNull(message = "Indique o número de páginas")
    private int pages;
    private String genre;

    @NotNull(message = "O livro deve ter um autor")
    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;



}
