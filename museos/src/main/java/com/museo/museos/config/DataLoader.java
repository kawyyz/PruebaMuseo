package com.museo.museos.config;

import com.museo.museos.model.Museo;
import com.museo.museos.repository.MuseoRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MuseoRepository museoRepository;

    @Override
    public void run(String... args) {

        if (museoRepository.count() == 0) {

            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {

                Museo museo = Museo.builder()
                        .nombre("Museo " + faker.artist().name())
                        .ciudad(faker.address().city())
                        .direccion(faker.address().streetAddress())
                        .descripcion(faker.lorem().sentence())
                        .horario("09:00 - 18:00")
                        .build();

                museoRepository.save(museo);
            }

            System.out.println("Museos Faker cargados correctamente");
        }
    }
}