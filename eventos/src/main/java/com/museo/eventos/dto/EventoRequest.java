package com.museo.eventos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    @NotNull
    private Long museoId;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private LocalDate fecha;
}
