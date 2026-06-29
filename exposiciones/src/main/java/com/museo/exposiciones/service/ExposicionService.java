package com.museo.exposiciones.service;

import com.museo.exposiciones.dto.ExposicionRequest;
import com.museo.exposiciones.dto.ExposicionResponse;

import java.util.List;

public interface ExposicionService {

    ExposicionResponse guardar(ExposicionRequest request);

    List<ExposicionResponse> listar();

    ExposicionResponse buscarPorId(Long id);

    ExposicionResponse actualizar(Long id, ExposicionRequest request);

    void eliminar(Long id);
}
