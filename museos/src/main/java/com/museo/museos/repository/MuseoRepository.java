package com.museo.museos.repository;

import com.museo.museos.model.Museo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseoRepository extends JpaRepository<Museo, Long> {
}
