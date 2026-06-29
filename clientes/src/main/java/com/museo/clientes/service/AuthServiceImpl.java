package com.museo.clientes.service;

import com.museo.clientes.dto.LoginRequest;
import com.museo.clientes.dto.LoginResponse;
import com.museo.clientes.model.Cliente;
import com.museo.clientes.repository.ClienteRepository;
import com.museo.clientes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getPassword(), cliente.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generarToken(cliente.getEmail(), cliente.getRol());

        return new LoginResponse(token);
    }
}
