package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/* HU13 y HU15: Mensajes a soporte técnico */
@Data
@Entity
@Table(name = "mensaje_soporte")
public class MensajeSoporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer idMensaje;

    private String nombre;
    private String email;
    private String asunto;
    private String mensaje;
    private LocalDateTime fecha;
}
