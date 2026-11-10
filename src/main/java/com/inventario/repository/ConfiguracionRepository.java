package com.inventario.repository;

import com.inventario.domain.Configuracion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {

    /* HU12: solo existe un registro de configuración */
    Optional<Configuracion> findFirstByOrderByIdConfiguracionAsc();
}
