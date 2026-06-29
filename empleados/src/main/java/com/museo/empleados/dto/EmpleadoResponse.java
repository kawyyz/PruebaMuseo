package com.museo.empleados.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse extends RepresentationModel<EmpleadoResponse> {

    private Long id;
    private Long museoId;
    private String nombre;
    private String cargo;
    private String email;
    private String telefono;
    private LocalDate fechaContratacion;
    private String estado;
}
