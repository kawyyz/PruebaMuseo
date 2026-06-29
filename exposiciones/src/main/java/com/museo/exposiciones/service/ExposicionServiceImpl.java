package com.museo.exposiciones.service;

import com.museo.exposiciones.controller.ExposicionController;
import com.museo.exposiciones.dto.ExposicionRequest;
import com.museo.exposiciones.dto.ExposicionResponse;
import com.museo.exposiciones.exception.ResourceNotFoundException;
import com.museo.exposiciones.model.Exposicion;
import com.museo.exposiciones.repository.ExposicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExposicionServiceImpl implements ExposicionService {

    private final ExposicionRepository repository;
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

    private ExposicionResponse convertir(Exposicion exposicion) {
        ExposicionResponse response = ExposicionResponse.builder()
                .id(exposicion.getId())
                .museoId(exposicion.getMuseoId())
                .titulo(exposicion.getTitulo())
                .descripcion(exposicion.getDescripcion())
                .fechaInicio(exposicion.getFechaInicio())
                .fechaFin(exposicion.getFechaFin())
                .estado(exposicion.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ExposicionController.class)
                                .buscarPorId(exposicion.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ExposicionController.class)
                                .listar()
                ).withRel("exposiciones")
        );

        return response;
    }

    @Override
    public ExposicionResponse guardar(ExposicionRequest request) {

        validarMuseo(request.getMuseoId());

        Exposicion exposicion = Exposicion.builder()
                .museoId(request.getMuseoId())
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .estado("ACTIVA")
                .build();

        return convertir(repository.save(exposicion));
    }

    @Override
    public List<ExposicionResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public ExposicionResponse buscarPorId(Long id) {
        Exposicion exposicion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exposición no encontrada"));

        return convertir(exposicion);
    }

    @Override
    public ExposicionResponse actualizar(Long id, ExposicionRequest request) {

        Exposicion exposicion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exposición no encontrada"));

        validarMuseo(request.getMuseoId());

        exposicion.setMuseoId(request.getMuseoId());
        exposicion.setTitulo(request.getTitulo());
        exposicion.setDescripcion(request.getDescripcion());
        exposicion.setFechaInicio(request.getFechaInicio());
        exposicion.setFechaFin(request.getFechaFin());

        return convertir(repository.save(exposicion));
    }

    @Override
    public void eliminar(Long id) {
        Exposicion exposicion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exposición no encontrada"));

        repository.delete(exposicion);
    }
}
