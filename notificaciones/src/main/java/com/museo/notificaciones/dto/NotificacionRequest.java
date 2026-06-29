package com.museo.notificaciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotNull
    private Long reservaId;

    @NotNull
    private Long pagoId;

    @NotNull
    private String mensaje;
}