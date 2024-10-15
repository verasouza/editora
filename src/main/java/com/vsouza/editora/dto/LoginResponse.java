package com.vsouza.editora.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private long expires;
}
