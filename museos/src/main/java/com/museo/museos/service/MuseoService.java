package com.museo.museos.service;

import com.museo.museos.dto.MuseoRequest;
import com.museo.museos.dto.MuseoResponse;

import java.util.List;

public interface MuseoService {

    MuseoResponse guardar(MuseoRequest request);

    List<MuseoResponse> listar();

    MuseoResponse buscarPorId(Long id);

    MuseoResponse actualizar(Long id, MuseoRequest request);

    void eliminar(Long id);
}