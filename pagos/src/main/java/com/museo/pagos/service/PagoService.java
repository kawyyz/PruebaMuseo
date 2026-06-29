package com.museo.pagos.service;

import com.museo.pagos.dto.PagoRequest;
import com.museo.pagos.dto.PagoResponse;

import java.util.List;

public interface PagoService {

    PagoResponse guardar(PagoRequest request);

    List<PagoResponse> listar();

    PagoResponse buscarPorId(Long id);

    PagoResponse actualizar(Long id, PagoRequest request);

    void eliminar(Long id);
}