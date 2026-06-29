package com.museo.clientes.config;

import com.museo.clientes.model.Cliente;
import com.museo.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Faker faker = new Faker();

        if (clienteRepository.count() == 0) {

            Cliente admin = Cliente.builder()
                    .nombre("Administrador")
                    .email("admin@email.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol("ADMIN")
                    .build();

            clienteRepository.save(admin);

            for (int i = 0; i < 20; i++) {

                Cliente cliente = Cliente.builder()
                        .nombre(faker.name().fullName())
                        .email("cliente" + i + "@email.com")
                        .password(passwordEncoder.encode("1234"))
                        .rol("CLIENTE")
                        .build();

                clienteRepository.save(cliente);
            }

            System.out.println("Datos Faker cargados correctamente");
        }
    }
}