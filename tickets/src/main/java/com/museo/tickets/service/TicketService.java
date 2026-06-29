package com.museo.tickets.service;

import com.museo.tickets.dto.TicketRequest;
import com.museo.tickets.dto.TicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse guardar(TicketRequest request);

    List<TicketResponse> listar();

    TicketResponse buscarPorId(Long id);

    TicketResponse actualizar(Long id, TicketRequest request);

    void eliminar(Long id);
}