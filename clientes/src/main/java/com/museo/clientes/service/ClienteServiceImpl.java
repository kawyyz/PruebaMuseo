package com.museo.clientes.service;

import com.museo.clientes.dto.ClienteRequest;
import com.museo.clientes.dto.ClienteResponse;
import com.museo.clientes.exception.ResourceNotFoundException;
import com.museo.clientes.model.Cliente;
import com.museo.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    private ClienteResponse convertir(Cliente cliente) {
        ClienteResponse response = ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .rol(cliente.getRol())
                .build();

        response.add(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                                com.museo.clientes.controller.ClienteController.class
                        ).buscarPorId(cliente.getId())
                ).withSelfRel()
        );

        response.add(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                                com.museo.clientes.controller.ClienteController.class
                        ).listar()
                ).withRel("clientes")
        );

        return response;
    }

    @Override
    public ClienteResponse guardar(ClienteRequest request) {

        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        return convertir(repository.save(cliente));
    }

    @Override
    public List<ClienteResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        return convertir(cliente);
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest request) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setRol(request.getRol());

        return convertir(repository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        repository.delete(cliente);
    }
}