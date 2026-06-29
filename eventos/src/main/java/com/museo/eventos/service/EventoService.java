package com.museo.eventos.service;

import com.museo.eventos.dto.EventoRequest;
import com.museo.eventos.dto.EventoResponse;

import java.util.List;

public interface EventoService {

    EventoResponse guardar(EventoRequest request);

    List<EventoResponse> listar();

    EventoResponse buscarPorId(Long id);

    EventoResponse actualizar(Long id, EventoRequest request);

    void eliminar(Long id);
}
