package com.inventario.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/* HU5: Categorías de productos */
@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    private String descripcion;
    private boolean activo = true;

    /* Asociación uno a muchos (Semana 7): una categoría tiene muchos productos */
    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Producto> productos;
}
