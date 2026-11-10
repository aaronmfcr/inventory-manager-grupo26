package com.inventario.service;

import com.inventario.domain.Categoria;
import com.inventario.repository.CategoriaRepository;
import com.inventario.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU5: Categorías de productos */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAllByOrderByDescripcionAsc();
    }

    @Transactional
    public void save(Categoria categoria) {
        if (categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("categoria.error.vacia");
        }
        categoria.setDescripcion(categoria.getDescripcion().trim());
        if (categoria.getIdCategoria() == null
                && categoriaRepository.existsByDescripcionIgnoreCase(categoria.getDescripcion())) {
            throw new IllegalArgumentException("categoria.error.existe");
        }
        categoriaRepository.save(categoria);
    }

    /* Solo se puede eliminar una categoría que no tenga productos */
    @Transactional
    public void delete(Integer idCategoria) {
        if (productoRepository.countByCategoriaIdCategoria(idCategoria) > 0) {
            throw new IllegalArgumentException("categoria.error.productos");
        }
        categoriaRepository.deleteById(idCategoria);
    }
}
