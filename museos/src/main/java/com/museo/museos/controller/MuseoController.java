package com.museo.museos.controller;

import com.museo.museos.dto.MuseoRequest;
import com.museo.museos.dto.MuseoResponse;
import com.museo.museos.service.MuseoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/museos")
@RequiredArgsConstructor
public class MuseoController {

    private final MuseoService service;

    @PostMapping
    public MuseoResponse guardar(@RequestBody @Valid MuseoRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<MuseoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MuseoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MuseoResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid MuseoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
