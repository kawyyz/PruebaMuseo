package com.museo.exposiciones.repository;

import com.museo.exposiciones.model.Exposicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposicionRepository extends JpaRepository<Exposicion, Long> {
}
