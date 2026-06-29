package com.museo.notificaciones.service;

import com.museo.notificaciones.dto.NotificacionRequest;
import com.museo.notificaciones.dto.NotificacionResponse;

import java.util.List;

public interface NotificacionService {

    NotificacionResponse guardar(NotificacionRequest request);

    List<NotificacionResponse> listar();

    NotificacionResponse buscarPorId(Long id);

    void eliminar(Long id);
}