package com.vsouza.editora.dto;

import lombok.Data;

@Data
public class BookRequest {

    private Long id;
    private String title;
    private String publisher;
    private String isbn;
    private int pages;
    private String genre;
    private Long authorId;
}
