package com.museo.pagos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {

    private Long reservaId;
    private Long pagoId;
    private Double precio;
}
