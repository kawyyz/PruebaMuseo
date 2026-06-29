package com.museo.museos.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuseoResponse extends RepresentationModel<MuseoResponse> {

    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String descripcion;
    private String horario;
}