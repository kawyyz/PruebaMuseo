package com.museo.empleados.service;

import com.museo.empleados.controller.EmpleadoController;
import com.museo.empleados.dto.EmpleadoRequest;
import com.museo.empleados.dto.EmpleadoResponse;
import com.museo.empleados.exception.ResourceNotFoundException;
import com.museo.empleados.model.Empleado;
import com.museo.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repository;
    private final WebClient.Builder webClientBuilder;

    private void validarMuseo(Long museoId) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://MS-MUSEOS/api/museos/" + museoId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Museo no existe");
        }
    }

    private EmpleadoResponse convertir(Empleado empleado) {
        EmpleadoResponse response = EmpleadoResponse.builder()
                .id(empleado.getId())
                .museoId(empleado.getMuseoId())
                .nombre(empleado.getNombre())
                .cargo(empleado.getCargo())
                .email(empleado.getEmail())
                .telefono(empleado.getTelefono())
                .fechaContratacion(empleado.getFechaContratacion())
                .estado(empleado.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EmpleadoController.class)
                                .buscarPorId(empleado.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EmpleadoController.class)
                                .listar()
                ).withRel("empleados")
        );

        return response;
    }

    @Override
    public EmpleadoResponse guardar(EmpleadoRequest request) {

        validarMuseo(request.getMuseoId());

        Empleado empleado = Empleado.builder()
                .museoId(request.getMuseoId())
                .nombre(request.getNombre())
                .cargo(request.getCargo())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .fechaContratacion(request.getFechaContratacion())
                .estado("ACTIVO")
                .build();

        return convertir(repository.save(empleado));
    }

    @Override
    public List<EmpleadoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public EmpleadoResponse buscarPorId(Long id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));

        return convertir(empleado);
    }

    @Override
    public EmpleadoResponse actualizar(Long id, EmpleadoRequest request) {

        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));

        validarMuseo(request.getMuseoId());

        empleado.setMuseoId(request.getMuseoId());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setEmail(request.getEmail());
        empleado.setTelefono(request.getTelefono());
        empleado.setFechaContratacion(request.getFechaContratacion());

        return convertir(repository.save(empleado));
    }

    @Override
    public void eliminar(Long id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));

        repository.delete(empleado);
    }
}