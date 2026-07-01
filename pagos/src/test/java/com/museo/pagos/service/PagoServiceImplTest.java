package com.museo.pagos.service;

import com.museo.pagos.dto.PagoResponse;
import com.museo.pagos.exception.ResourceNotFoundException;
import com.museo.pagos.model.Pago;
import com.museo.pagos.repository.PagoRepository;
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
class PagoServiceImplTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private PagoServiceImpl service;

    @Test
    void listar_deberiaRetornarListaDePagos() {
        Pago pago = Pago.builder()
                .id(1L)
                .reservaId(1L)
                .monto(15000)
                .metodoPago("DEBITO")
                .fechaPago(LocalDate.now())
                .estado("PAGADO")
                .build();

        when(repository.findAll()).thenReturn(List.of(pago));

        List<PagoResponse> resultado = service.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("PAGADO", resultado.get(0).getEstado());

        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarPago() {
        Pago pago = Pago.builder()
                .id(1L)
                .reservaId(1L)
                .monto(15000)
                .metodoPago("DEBITO")
                .fechaPago(LocalDate.now())
                .estado("PAGADO")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(pago));

        PagoResponse response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getReservaId());
        assertEquals("DEBITO", response.getMetodoPago());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }

    @Test
    void eliminar_cuandoExiste_deberiaEliminarPago() {
        Pago pago = Pago.builder()
                .id(1L)
                .reservaId(1L)
                .monto(15000)
                .metodoPago("DEBITO")
                .fechaPago(LocalDate.now())
                .estado("PAGADO")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(pago));

        service.eliminar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(pago);
    }
}
