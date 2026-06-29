package com.museo.eventos.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoResponse extends RepresentationModel<EventoResponse> {

    private Long id;
    private Long museoId;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private String estado;
}
