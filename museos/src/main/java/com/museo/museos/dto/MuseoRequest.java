package com.museo.museos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MuseoRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String ciudad;

    @NotBlank
    private String direccion;

    private String descripcion;

    private String horario;
}