package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/* HU12: Configuración de la información del negocio */
@Data
@Entity
@Table(name = "configuracion")
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Integer idConfiguracion;

    @Column(name = "nombre_negocio")
    private String nombreNegocio;

    private String email;
    private String telefono;
    private String direccion;
}
