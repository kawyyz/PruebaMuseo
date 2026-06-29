package com.museo.tickets.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse extends RepresentationModel<TicketResponse> {

    private Long id;
    private Long reservaId;
    private Long pagoId;
    private Double precio;
    private String codigoQr;
    private LocalDateTime fechaEmision;
    private String estado;
}