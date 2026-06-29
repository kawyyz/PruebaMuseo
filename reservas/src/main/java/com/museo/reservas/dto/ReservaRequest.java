package com.museo.reservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long museoId;

    @NotNull
    private LocalDate fechaReserva;

    @NotNull
    private Integer cantidadPersonas;
}
