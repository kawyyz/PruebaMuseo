package com.museo.salas.service;

import com.museo.salas.dto.SalaRequest;
import com.museo.salas.dto.SalaResponse;

import java.util.List;

public interface SalaService {

    SalaResponse guardar(SalaRequest request);

    List<SalaResponse> listar();

    SalaResponse buscarPorId(Long id);

    SalaResponse actualizar(Long id, SalaRequest request);

    void eliminar(Long id);
}
