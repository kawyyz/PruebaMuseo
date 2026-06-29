package com.museo.clientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotBlank
    private String nombre;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String rol;
}
