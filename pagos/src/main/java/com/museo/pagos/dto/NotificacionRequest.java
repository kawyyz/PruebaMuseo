package com.museo.pagos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacionRequest {

    private Long reservaId;
    private Long pagoId;
    private String mensaje;
}