package com.museo.empleados.controller;

import com.museo.empleados.dto.EmpleadoRequest;
import com.museo.empleados.dto.EmpleadoResponse;
import com.museo.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @PostMapping
    public EmpleadoResponse guardar(@RequestBody @Valid EmpleadoRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<EmpleadoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EmpleadoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EmpleadoResponse actualizar(
            @PathVariable Long id,
            @RequestBody @Valid EmpleadoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
