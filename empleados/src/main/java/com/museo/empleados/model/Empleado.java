package com.museo.empleados.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long museoId;

    private String nombre;

    private String cargo;

    private String email;

    private String telefono;

    private LocalDate fechaContratacion;

    private String estado;
}
