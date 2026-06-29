package com.museo.pagos.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponse extends RepresentationModel<PagoResponse> {

    private Long id;
    private Long reservaId;
    private Integer monto;
    private String metodoPago;
    private LocalDate fechaPago;
    private String estado;
}
