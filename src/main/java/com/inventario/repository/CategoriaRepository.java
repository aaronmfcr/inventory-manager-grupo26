package com.inventario.repository;

import com.inventario.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    /* Consulta derivada: categorías ordenadas por nombre */
    List<Categoria> findAllByOrderByDescripcionAsc();

    boolean existsByDescripcionIgnoreCase(String descripcion);
}
