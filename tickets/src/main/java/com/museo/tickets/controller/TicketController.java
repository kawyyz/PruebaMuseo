package com.museo.tickets.controller;

import com.museo.tickets.dto.TicketRequest;
import com.museo.tickets.dto.TicketResponse;
import com.museo.tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @PostMapping
    public TicketResponse guardar(@RequestBody @Valid TicketRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<TicketResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TicketResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TicketResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid TicketRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}