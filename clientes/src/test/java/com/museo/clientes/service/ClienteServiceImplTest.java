package com.museo.clientes.service;

import com.museo.clientes.dto.ClienteRequest;
import com.museo.clientes.dto.ClienteResponse;
import com.museo.clientes.exception.ResourceNotFoundException;
import com.museo.clientes.model.Cliente;
import com.museo.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteServiceImpl service;

    @Test
    void guardar_deberiaGuardarClienteYRetornarResponse() {
        // Given
        ClienteRequest request = new ClienteRequest();
        request.setNombre("Arion");
        request.setEmail("arion@test.com");
        request.setPassword("1234");
        request.setRol("USER");

        Cliente clienteGuardado = Cliente.builder()
                .id(1L)
                .nombre("Arion")
                .email("arion@test.com")
                .password("password_encriptada")
                .rol("USER")
                .build();

        when(passwordEncoder.encode("1234")).thenReturn("password_encriptada");
        when(repository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        // When
        ClienteResponse response = service.guardar(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Arion", response.getNombre());
        assertEquals("arion@test.com", response.getEmail());
        assertEquals("USER", response.getRol());

        verify(passwordEncoder).encode("1234");
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void listar_deberiaRetornarListaDeClientes() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Arion")
                .email("arion@test.com")
                .rol("USER")
                .build();

        when(repository.findAll()).thenReturn(List.of(cliente));

        // When
        List<ClienteResponse> resultado = service.listar();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Arion", resultado.get(0).getNombre());

        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarCliente() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Arion")
                .email("arion@test.com")
                .rol("USER")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        ClienteResponse response = service.buscarPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Arion", response.getNombre());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.buscarPorId(99L);
        });

        verify(repository).findById(99L);
    }

    @Test
    void actualizar_cuandoExiste_deberiaActualizarCliente() {
        // Given
        ClienteRequest request = new ClienteRequest();
        request.setNombre("Nuevo Nombre");
        request.setEmail("nuevo@test.com");
        request.setPassword("abcd");
        request.setRol("ADMIN");

        Cliente clienteExistente = Cliente.builder()
                .id(1L)
                .nombre("Arion")
                .email("arion@test.com")
                .password("old")
                .rol("USER")
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(1L)
                .nombre("Nuevo Nombre")
                .email("nuevo@test.com")
                .password("password_encriptada")
                .rol("ADMIN")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(passwordEncoder.encode("abcd")).thenReturn("password_encriptada");
        when(repository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        ClienteResponse response = service.actualizar(1L, request);

        // Then
        assertEquals("Nuevo Nombre", response.getNombre());
        assertEquals("nuevo@test.com", response.getEmail());
        assertEquals("ADMIN", response.getRol());

        verify(repository).findById(1L);
        verify(passwordEncoder).encode("abcd");
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void eliminar_cuandoExiste_deberiaEliminarCliente() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Arion")
                .email("arion@test.com")
                .rol("USER")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        service.eliminar(1L);

        // Then
        verify(repository).findById(1L);
        verify(repository).delete(cliente);
    }
}
