package com.museo.clientes.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse extends RepresentationModel<ClienteResponse> {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
}