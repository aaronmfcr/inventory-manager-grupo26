package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/* HU1, HU2, HU3: Productos del inventario */
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    private String sku;
    private String nombre;
    private BigDecimal precio;
    private Integer existencias;

    /* HU7: cantidad mínima para generar la alerta de stock bajo */
    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    private boolean activo = true;

    /* Asociación muchos a uno (Semana 6): cada producto pertenece a una categoría */
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    /* Estado que se muestra en la tabla: EN_STOCK, STOCK_BAJO o SIN_STOCK */
    public String getEstadoStock() {
        int stock = existencias == null ? 0 : existencias;
        int minimo = stockMinimo == null ? 0 : stockMinimo;
        if (stock == 0) {
            return "SIN_STOCK";
        }
        if (stock <= minimo) {
            return "STOCK_BAJO";
        }
        return "EN_STOCK";
    }
}
