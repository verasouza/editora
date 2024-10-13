package com.vsouza.editora.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class AuthorDTO {

    private Long id;
    @NotEmpty(message = "Necessário preencher o nome!")
    private String fullName;
    @NotEmpty(message = "Necessário preencher o e-mail!")
    private String email;
}
