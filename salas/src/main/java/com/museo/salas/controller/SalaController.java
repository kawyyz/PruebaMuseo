package com.museo.salas.controller;

import com.museo.salas.dto.SalaRequest;
import com.museo.salas.dto.SalaResponse;
import com.museo.salas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService service;

    @PostMapping
    public SalaResponse guardar(@RequestBody @Valid SalaRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<SalaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SalaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public SalaResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid SalaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
