package com.museo.reservas.controller;

import com.museo.reservas.dto.ReservaRequest;
import com.museo.reservas.dto.ReservaResponse;
import com.museo.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @PostMapping
    public ReservaResponse guardar(@RequestBody @Valid ReservaRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<ReservaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ReservaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ReservaResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid ReservaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
