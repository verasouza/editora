package com.vsouza.editora.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class AuthorDTO {

    private Long id;
    @NotEmpty(message = "Necessário preencher o nome!")
    private String firstName;
    @NotEmpty(message = "Necessário preencher o sobrenome!")
    private String lastName;
    @NotEmpty(message = "Necessário preencher o e-mail!")
    private String email;
}
