package com.museo.empleados.service;

import com.museo.empleados.dto.EmpleadoRequest;
import com.museo.empleados.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {

    EmpleadoResponse guardar(EmpleadoRequest request);

    List<EmpleadoResponse> listar();

    EmpleadoResponse buscarPorId(Long id);

    EmpleadoResponse actualizar(Long id, EmpleadoRequest request);

    void eliminar(Long id);
}