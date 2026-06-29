package com.museo.pagos.service;

import com.museo.pagos.controller.PagoController;
import com.museo.pagos.dto.PagoRequest;
import com.museo.pagos.dto.PagoResponse;
import com.museo.pagos.dto.TicketRequest;
import com.museo.pagos.exception.ResourceNotFoundException;
import com.museo.pagos.model.Pago;
import com.museo.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.museo.pagos.dto.NotificacionRequest;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;
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

    private PagoResponse convertir(Pago pago) {
        PagoResponse response = PagoResponse.builder()
                .id(pago.getId())
                .reservaId(pago.getReservaId())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .fechaPago(pago.getFechaPago())
                .estado(pago.getEstado())
                .build();

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PagoController.class)
                                .buscarPorId(pago.getId())
                ).withSelfRel()
        );

        response.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PagoController.class)
                                .listar()
                ).withRel("pagos")
        );

        return response;
    }

    @Override
    public PagoResponse guardar(PagoRequest request) {

        validarReserva(request.getReservaId());

        Pago pago = Pago.builder()
                .reservaId(request.getReservaId())
                .monto(request.getMonto())
                .metodoPago(request.getMetodoPago())
                .fechaPago(LocalDate.now())
                .estado("PAGADO")
                .build();

        Pago pagoGuardado = repository.save(pago);

        generarNotificacion(pagoGuardado);
        generarTicket(pagoGuardado);

        return convertir(pagoGuardado);
    }

    @Override
    public List<PagoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public PagoResponse buscarPorId(Long id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        return convertir(pago);
    }

    @Override
    public PagoResponse actualizar(Long id, PagoRequest request) {

        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        validarReserva(request.getReservaId());

        pago.setReservaId(request.getReservaId());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());

        return convertir(repository.save(pago));
    }

    @Override
    public void eliminar(Long id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        repository.delete(pago);
    }

    private void generarNotificacion(Pago pago) {
        try {
            NotificacionRequest request = NotificacionRequest.builder()
                    .reservaId(pago.getReservaId())
                    .pagoId(pago.getId())
                    .mensaje("Pago confirmado correctamente")
                    .build();

            webClientBuilder.build()
                    .post()
                    .uri("http://MS-NOTIFICACIONES/api/notificaciones")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            System.out.println("No se pudo enviar la notificación: " + e.getMessage());
        }
    }

    private void generarTicket(Pago pago) {

        TicketRequest ticketRequest = TicketRequest.builder()
                .reservaId(pago.getReservaId())
                .pagoId(pago.getId())
                .precio(Double.valueOf(pago.getMonto()))
                .build();

        try {
            webClientBuilder.build()
                    .post()
                    .uri("http://MS-TICKETS/api/tickets")
                    .bodyValue(ticketRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            System.out.println("No se pudo generar el ticket: " + e.getMessage());
        }
    }
}