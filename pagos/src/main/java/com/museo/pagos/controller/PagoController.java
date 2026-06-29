package com.museo.pagos.controller;

import com.museo.pagos.dto.PagoRequest;
import com.museo.pagos.dto.PagoResponse;
import com.museo.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @PostMapping
    public PagoResponse guardar(@RequestBody @Valid PagoRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<PagoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PagoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PagoResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid PagoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
