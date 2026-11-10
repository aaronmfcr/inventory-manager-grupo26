package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/* HU9: Creación de pedidos y HU13: Estatus de pedidos */
@Data
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Estados posibles de un pedido */
    public static final String PROCESANDO = "PROCESANDO";
    public static final String COMPLETADO = "COMPLETADO";
    public static final String CANCELADO = "CANCELADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private LocalDateTime fecha;
    private Integer cantidad;
    private String proveedor;
    private String estado;
}
