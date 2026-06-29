package com.museo.exposiciones.controller;

import com.museo.exposiciones.dto.ExposicionRequest;
import com.museo.exposiciones.dto.ExposicionResponse;
import com.museo.exposiciones.service.ExposicionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exposiciones")
@RequiredArgsConstructor
public class ExposicionController {

    private final ExposicionService service;

    @PostMapping
    public ExposicionResponse guardar(@RequestBody @Valid ExposicionRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<ExposicionResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ExposicionResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ExposicionResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid ExposicionRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
