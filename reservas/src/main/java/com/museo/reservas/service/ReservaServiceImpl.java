package com.museo.reservas.service;

import com.museo.reservas.controller.ReservaController;
import com.museo.reservas.dto.ReservaRequest;
import com.museo.reservas.dto.ReservaResponse;
import com.museo.reservas.exception.ResourceNotFoundException;
import com.museo.reservas.model.Reserva;
import com.museo.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository repository;
    private final WebClient.Builder webClientBuilder;

    private void validarCliente(Long clienteId) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://MS-CLIENTES/api/clientes/" + clienteId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Cliente no existe");
        }
    }

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

    private ReservaResponse convertir(Reserva reserva) {
        ReservaResponse response = ReservaResponse.builder()
                .id(reserva.getId())
                .clienteId(reserva.getClienteId())
                .museoId(reserva.getMuseoId())
                .fechaReserva(reserva.getFechaReserva())
                .cantidadPersonas(reserva.getCantidadPersonas())
                .estado(reserva.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ReservaController.class)
                                .buscarPorId(reserva.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ReservaController.class)
                                .listar()
                ).withRel("reservas")
        );

        return response;
    }

    @Override
    public ReservaResponse guardar(ReservaRequest request) {

        validarCliente(request.getClienteId());
        validarMuseo(request.getMuseoId());

        Reserva reserva = Reserva.builder()
                .clienteId(request.getClienteId())
                .museoId(request.getMuseoId())
                .fechaReserva(request.getFechaReserva())
                .cantidadPersonas(request.getCantidadPersonas())
                .estado("PENDIENTE")
                .build();

        return convertir(repository.save(reserva));
    }

    @Override
    public List<ReservaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public ReservaResponse buscarPorId(Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        return convertir(reserva);
    }

    @Override
    public ReservaResponse actualizar(Long id, ReservaRequest request) {

        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        validarCliente(request.getClienteId());
        validarMuseo(request.getMuseoId());

        reserva.setClienteId(request.getClienteId());
        reserva.setMuseoId(request.getMuseoId());
        reserva.setFechaReserva(request.getFechaReserva());
        reserva.setCantidadPersonas(request.getCantidadPersonas());

        return convertir(repository.save(reserva));
    }

    @Override
    public void eliminar(Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        repository.delete(reserva);
    }
}
