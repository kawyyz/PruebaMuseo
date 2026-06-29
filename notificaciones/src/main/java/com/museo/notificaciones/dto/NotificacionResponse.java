package com.museo.notificaciones.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponse extends RepresentationModel<NotificacionResponse> {

    private Long id;
    private Long reservaId;
    private Long pagoId;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String estado;
}
