package com.inventario.service;

import com.inventario.domain.Configuracion;
import com.inventario.repository.ConfiguracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU12: Configuración del negocio */
@Service
public class ConfiguracionService {

    private final ConfiguracionRepository configuracionRepository;

    public ConfiguracionService(ConfiguracionRepository configuracionRepository) {
        this.configuracionRepository = configuracionRepository;
    }

    /* Devuelve la configuración guardada o una nueva si aún no existe */
    @Transactional(readOnly = true)
    public Configuracion getConfiguracion() {
        return configuracionRepository.findFirstByOrderByIdConfiguracionAsc().orElseGet(() -> {
            var configuracion = new Configuracion();
            configuracion.setNombreNegocio("Inventory Manager");
            return configuracion;
        });
    }

    @Transactional
    public void save(Configuracion configuracion) {
        if (configuracion.getNombreNegocio() == null || configuracion.getNombreNegocio().isBlank()) {
            throw new IllegalArgumentException("configuracion.error.nombre");
        }
        configuracionRepository.save(configuracion);
    }
}
