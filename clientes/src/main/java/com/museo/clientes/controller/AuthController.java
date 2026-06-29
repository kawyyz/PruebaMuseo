package com.museo.clientes.controller;

import com.museo.clientes.dto.LoginRequest;
import com.museo.clientes.dto.LoginResponse;
import com.museo.clientes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
