package com.museo.clientes.service;

import com.museo.clientes.dto.ClienteRequest;
import com.museo.clientes.dto.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse guardar(ClienteRequest request);

    List<ClienteResponse> listar();

    ClienteResponse buscarPorId(Long id);

    ClienteResponse actualizar(Long id, ClienteRequest request);

    void eliminar(Long id);
}
