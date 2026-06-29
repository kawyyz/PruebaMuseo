package com.museo.pagos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotNull
    private Long reservaId;

    @NotNull
    private Integer monto;

    @NotNull
    private String metodoPago;
}
