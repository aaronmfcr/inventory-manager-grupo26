package com.inventario.repository;

import com.inventario.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /* Listado general ordenado por SKU */
    List<Producto> findAllByOrderBySkuAsc();

    /* HU4: búsqueda de productos por nombre, SKU o categoría (consulta JPQL - Semana 8) */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) "
            + "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :texto, '%')) "
            + "OR LOWER(p.categoria.descripcion) LIKE LOWER(CONCAT('%', :texto, '%')) ORDER BY p.sku")
    List<Producto> buscar(@Param("texto") String texto);

    /* HU5: productos de una categoría */
    List<Producto> findByCategoriaIdCategoriaOrderBySkuAsc(Integer idCategoria);

    /* HU7: productos con stock igual o menor al mínimo (consulta JPQL - Semana 8) */
    @Query("SELECT p FROM Producto p WHERE p.existencias <= p.stockMinimo ORDER BY p.existencias ASC")
    List<Producto> findStockBajo();

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.existencias <= p.stockMinimo")
    long countStockBajo();

    boolean existsBySkuIgnoreCase(String sku);

    boolean existsBySkuIgnoreCaseAndIdProductoNot(String sku, Integer idProducto);

    long countByCategoriaIdCategoria(Integer idCategoria);
}
