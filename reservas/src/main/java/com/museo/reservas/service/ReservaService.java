package com.museo.reservas.service;

import com.museo.reservas.dto.ReservaRequest;
import com.museo.reservas.dto.ReservaResponse;

import java.util.List;

public interface ReservaService {

    ReservaResponse guardar(ReservaRequest request);

    List<ReservaResponse> listar();

    ReservaResponse buscarPorId(Long id);

    ReservaResponse actualizar(Long id, ReservaRequest request);

    void eliminar(Long id);
}
