package com.museo.reservas.service;

import com.museo.reservas.dto.ReservaResponse;
import com.museo.reservas.exception.ResourceNotFoundException;
import com.museo.reservas.model.Reserva;
import com.museo.reservas.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ReservaServiceImpl service;

    @Test
    void listar_deberiaRetornarLista() {

        Reserva reserva = Reserva.builder()
                .id(1L)
                .clienteId(1L)
                .museoId(1L)
                .fechaReserva(LocalDate.now())
                .cantidadPersonas(2)
                .estado("PENDIENTE")
                .build();

        when(repository.findAll()).thenReturn(List.of(reserva));

        List<ReservaResponse> resultado = service.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());

        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarReserva() {

        Reserva reserva = Reserva.builder()
                .id(1L)
                .clienteId(1L)
                .museoId(1L)
                .fechaReserva(LocalDate.now())
                .cantidadPersonas(2)
                .estado("PENDIENTE")
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(reserva));

        ReservaResponse response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }

    @Test
    void eliminar_cuandoExiste_deberiaEliminarReserva() {

        Reserva reserva = Reserva.builder()
                .id(1L)
                .clienteId(1L)
                .museoId(1L)
                .fechaReserva(LocalDate.now())
                .cantidadPersonas(2)
                .estado("PENDIENTE")
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(reserva));

        service.eliminar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(reserva);
    }
}
