package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/* HU7: Alertas de stock bajo y HU10: Sección de notificaciones */
@Data
@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Tipos de notificación (el texto se traduce con messages.properties) */
    public static final String STOCK_BAJO = "STOCK_BAJO";
    public static final String SIN_STOCK = "SIN_STOCK";
    public static final String PEDIDO_CREADO = "PEDIDO_CREADO";
    public static final String PEDIDO_COMPLETADO = "PEDIDO_COMPLETADO";
    public static final String MENSAJE = "MENSAJE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    private String tipo;
    private String referencia;
    private Integer cantidad;
    private LocalDateTime fecha;
    private boolean leida;
}
