package com.museo.clientes.service;

import com.museo.clientes.dto.LoginRequest;
import com.museo.clientes.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}