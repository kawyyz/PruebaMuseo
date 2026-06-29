package com.museo.reservas.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponse extends RepresentationModel<ReservaResponse> {

    private Long id;
    private Long clienteId;
    private Long museoId;
    private LocalDate fechaReserva;
    private Integer cantidadPersonas;
    private String estado;
}
