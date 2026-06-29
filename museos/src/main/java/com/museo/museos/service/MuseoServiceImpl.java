package com.museo.museos.service;

import com.museo.museos.controller.MuseoController;
import com.museo.museos.dto.MuseoRequest;
import com.museo.museos.dto.MuseoResponse;
import com.museo.museos.exception.ResourceNotFoundException;
import com.museo.museos.model.Museo;
import com.museo.museos.repository.MuseoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuseoServiceImpl implements MuseoService {

    private final MuseoRepository repository;

    private MuseoResponse convertir(Museo museo) {
        MuseoResponse response = MuseoResponse.builder()
                .id(museo.getId())
                .nombre(museo.getNombre())
                .ciudad(museo.getCiudad())
                .direccion(museo.getDireccion())
                .descripcion(museo.getDescripcion())
                .horario(museo.getHorario())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(MuseoController.class)
                                .buscarPorId(museo.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(MuseoController.class)
                                .listar()
                ).withRel("museos")
        );

        return response;
    }

    @Override
    public MuseoResponse guardar(MuseoRequest request) {
        Museo museo = Museo.builder()
                .nombre(request.getNombre())
                .ciudad(request.getCiudad())
                .direccion(request.getDireccion())
                .descripcion(request.getDescripcion())
                .horario(request.getHorario())
                .build();

        return convertir(repository.save(museo));
    }

    @Override
    public List<MuseoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public MuseoResponse buscarPorId(Long id) {
        Museo museo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Museo no encontrado"));

        return convertir(museo);
    }

    @Override
    public MuseoResponse actualizar(Long id, MuseoRequest request) {
        Museo museo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Museo no encontrado"));

        museo.setNombre(request.getNombre());
        museo.setCiudad(request.getCiudad());
        museo.setDireccion(request.getDireccion());
        museo.setDescripcion(request.getDescripcion());
        museo.setHorario(request.getHorario());

        return convertir(repository.save(museo));
    }

    @Override
    public void eliminar(Long id) {
        Museo museo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Museo no encontrado"));

        repository.delete(museo);
    }
}