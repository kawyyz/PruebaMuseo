package com.museo.exposiciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExposicionRequest {

    @NotNull
    private Long museoId;

    @NotNull
    private String titulo;

    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;
}
