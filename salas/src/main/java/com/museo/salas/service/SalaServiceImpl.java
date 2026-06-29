package com.museo.salas.service;

import com.museo.salas.controller.SalaController;
import com.museo.salas.dto.SalaRequest;
import com.museo.salas.dto.SalaResponse;
import com.museo.salas.exception.ResourceNotFoundException;
import com.museo.salas.model.Sala;
import com.museo.salas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaServiceImpl implements SalaService {

    private final SalaRepository repository;
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

    private SalaResponse convertir(Sala sala) {
        SalaResponse response = SalaResponse.builder()
                .id(sala.getId())
                .museoId(sala.getMuseoId())
                .nombre(sala.getNombre())
                .capacidad(sala.getCapacidad())
                .tipo(sala.getTipo())
                .estado(sala.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SalaController.class)
                                .buscarPorId(sala.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SalaController.class)
                                .listar()
                ).withRel("salas")
        );

        return response;
    }

    @Override
    public SalaResponse guardar(SalaRequest request) {

        validarMuseo(request.getMuseoId());

        Sala sala = Sala.builder()
                .museoId(request.getMuseoId())
                .nombre(request.getNombre())
                .capacidad(request.getCapacidad())
                .tipo(request.getTipo())
                .estado("DISPONIBLE")
                .build();

        return convertir(repository.save(sala));
    }

    @Override
    public List<SalaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public SalaResponse buscarPorId(Long id) {
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada"));

        return convertir(sala);
    }

    @Override
    public SalaResponse actualizar(Long id, SalaRequest request) {

        Sala sala = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada"));

        validarMuseo(request.getMuseoId());

        sala.setMuseoId(request.getMuseoId());
        sala.setNombre(request.getNombre());
        sala.setCapacidad(request.getCapacidad());
        sala.setTipo(request.getTipo());

        return convertir(repository.save(sala));
    }

    @Override
    public void eliminar(Long id) {
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada"));

        repository.delete(sala);
    }
}