package com.museo.eventos.service;

import com.museo.eventos.controller.EventoController;
import com.museo.eventos.dto.EventoRequest;
import com.museo.eventos.dto.EventoResponse;
import com.museo.eventos.exception.ResourceNotFoundException;
import com.museo.eventos.model.Evento;
import com.museo.eventos.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository repository;
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

    private EventoResponse convertir(Evento evento) {
        EventoResponse response = EventoResponse.builder()
                .id(evento.getId())
                .museoId(evento.getMuseoId())
                .nombre(evento.getNombre())
                .descripcion(evento.getDescripcion())
                .fecha(evento.getFecha())
                .estado(evento.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EventoController.class)
                                .buscarPorId(evento.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EventoController.class)
                                .listar()
                ).withRel("eventos")
        );

        return response;
    }

    @Override
    public EventoResponse guardar(EventoRequest request) {

        validarMuseo(request.getMuseoId());

        Evento evento = Evento.builder()
                .museoId(request.getMuseoId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .estado("PROGRAMADO")
                .build();

        return convertir(repository.save(evento));
    }

    @Override
    public List<EventoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public EventoResponse buscarPorId(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        return convertir(evento);
    }

    @Override
    public EventoResponse actualizar(Long id, EventoRequest request) {

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        validarMuseo(request.getMuseoId());

        evento.setMuseoId(request.getMuseoId());
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFecha(request.getFecha());

        return convertir(repository.save(evento));
    }

    @Override
    public void eliminar(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        repository.delete(evento);
    }
}
