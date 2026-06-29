package com.museo.tickets.service;

import com.museo.tickets.controller.TicketController;
import com.museo.tickets.dto.TicketRequest;
import com.museo.tickets.dto.TicketResponse;
import com.museo.tickets.exception.ResourceNotFoundException;
import com.museo.tickets.model.Ticket;
import com.museo.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repository;
    private final WebClient.Builder webClientBuilder;

    private void validarReserva(Long reservaId) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://MS-RESERVAS/api/reservas/" + reservaId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Reserva no existe");
        }
    }

    private void validarPago(Long pagoId) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://MS-PAGOS/api/pagos/" + pagoId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Pago no existe");
        }
    }

    private TicketResponse convertir(Ticket ticket) {
        TicketResponse response = TicketResponse.builder()
                .id(ticket.getId())
                .reservaId(ticket.getReservaId())
                .pagoId(ticket.getPagoId())
                .precio(ticket.getPrecio())
                .codigoQr(ticket.getCodigoQr())
                .fechaEmision(ticket.getFechaEmision())
                .estado(ticket.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(TicketController.class)
                                .buscarPorId(ticket.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(TicketController.class)
                                .listar()
                ).withRel("tickets")
        );

        return response;
    }

    @Override
    public TicketResponse guardar(TicketRequest request) {

        validarReserva(request.getReservaId());
        validarPago(request.getPagoId());

        Ticket ticket = Ticket.builder()
                .reservaId(request.getReservaId())
                .pagoId(request.getPagoId())
                .precio(request.getPrecio())
                .codigoQr("QR-" + UUID.randomUUID())
                .fechaEmision(LocalDateTime.now())
                .estado("ACTIVO")
                .build();

        return convertir(repository.save(ticket));
    }

    @Override
    public List<TicketResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public TicketResponse buscarPorId(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        return convertir(ticket);
    }

    @Override
    public TicketResponse actualizar(Long id, TicketRequest request) {

        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        validarReserva(request.getReservaId());
        validarPago(request.getPagoId());

        ticket.setReservaId(request.getReservaId());
        ticket.setPagoId(request.getPagoId());
        ticket.setPrecio(request.getPrecio());

        return convertir(repository.save(ticket));
    }

    @Override
    public void eliminar(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        repository.delete(ticket);
    }
}