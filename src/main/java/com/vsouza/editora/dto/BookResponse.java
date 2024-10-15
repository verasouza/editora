package com.vsouza.editora.dto;

import lombok.Data;

@Data
public class BookResponse {

    private String title;
    private String publisher;
    private String isbn;
    private int pages;
    private String genre;
    private String author;
    private int quantity;
    private int publishYear;
}
