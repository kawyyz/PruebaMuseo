package com.museo.notificaciones.controller;

import com.museo.notificaciones.dto.NotificacionRequest;
import com.museo.notificaciones.dto.NotificacionResponse;
import com.museo.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @PostMapping
    public NotificacionResponse guardar(@RequestBody @Valid NotificacionRequest request) {
        return service.guardar(request);
    }

    @GetMapping
    public List<NotificacionResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public NotificacionResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
