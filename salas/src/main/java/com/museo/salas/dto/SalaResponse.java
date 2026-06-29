package com.museo.salas.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaResponse extends RepresentationModel<SalaResponse> {

    private Long id;
    private Long museoId;
    private String nombre;
    private Integer capacidad;
    private String tipo;
    private String estado;
}