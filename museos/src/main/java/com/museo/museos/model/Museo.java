package com.museo.museos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "museos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Museo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String ciudad;

    @NotBlank
    private String direccion;

    private String descripcion;

    private String horario;
}
