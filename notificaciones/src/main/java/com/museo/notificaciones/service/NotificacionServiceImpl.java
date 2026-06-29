package com.museo.notificaciones.service;

import com.museo.notificaciones.controller.NotificacionController;
import com.museo.notificaciones.dto.NotificacionRequest;
import com.museo.notificaciones.dto.NotificacionResponse;
import com.museo.notificaciones.exception.ResourceNotFoundException;
import com.museo.notificaciones.model.Notificacion;
import com.museo.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;

    private NotificacionResponse convertir(Notificacion notificacion) {
        NotificacionResponse response = NotificacionResponse.builder()
                .id(notificacion.getId())
                .reservaId(notificacion.getReservaId())
                .pagoId(notificacion.getPagoId())
                .mensaje(notificacion.getMensaje())
                .fechaEnvio(notificacion.getFechaEnvio())
                .estado(notificacion.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(NotificacionController.class)
                                .buscarPorId(notificacion.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(NotificacionController.class)
                                .listar()
                ).withRel("notificaciones")
        );

        return response;
    }

    @Override
    public NotificacionResponse guardar(NotificacionRequest request) {

        Notificacion notificacion = Notificacion.builder()
                .reservaId(request.getReservaId())
                .pagoId(request.getPagoId())
                .mensaje(request.getMensaje())
                .fechaEnvio(LocalDateTime.now())
                .estado("ENVIADA")
                .build();

        return convertir(repository.save(notificacion));
    }

    @Override
    public List<NotificacionResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public NotificacionResponse buscarPorId(Long id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));

        return convertir(notificacion);
    }

    @Override
    public void eliminar(Long id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));

        repository.delete(notificacion);
    }
}