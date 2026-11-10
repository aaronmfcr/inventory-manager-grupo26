package com.inventario.service;

import com.inventario.domain.Producto;
import com.inventario.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU1, HU2, HU3 (CRUD), HU4 (búsqueda) y HU8 (actualización de stock) */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final NotificacionService notificacionService;

    public ProductoService(ProductoRepository productoRepository, NotificacionService notificacionService) {
        this.productoRepository = productoRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findAllByOrderBySkuAsc();
    }

    /* HU4: búsqueda por nombre, SKU o categoría */
    @Transactional(readOnly = true)
    public List<Producto> buscar(String texto) {
        return productoRepository.buscar(texto);
    }

    /* HU5: filtrar por categoría */
    @Transactional(readOnly = true)
    public List<Producto> getProductosPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoriaOrderBySkuAsc(idCategoria);
    }

    /* HU7: productos en stock bajo o sin stock */
    @Transactional(readOnly = true)
    public List<Producto> getStockBajo() {
        return productoRepository.findStockBajo();
    }

    @Transactional(readOnly = true)
    public long getCantidadStockBajo() {
        return productoRepository.countStockBajo();
    }

    @Transactional(readOnly = true)
    public long getTotalProductos() {
        return productoRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    /* HU1 (nuevo) y HU2 (edición) */
    @Transactional
    public void save(Producto producto) {
        producto.setSku(producto.getSku().trim().toUpperCase());
        boolean skuRepetido = producto.getIdProducto() == null
                ? productoRepository.existsBySkuIgnoreCase(producto.getSku())
                : productoRepository.existsBySkuIgnoreCaseAndIdProductoNot(producto.getSku(), producto.getIdProducto());
        if (skuRepetido) {
            throw new IllegalArgumentException("producto.error.sku");
        }
        if (producto.getStockMinimo() == null) {
            producto.setStockMinimo(5);
        }
        productoRepository.save(producto);
        notificacionService.revisarStock(producto);
    }

    /* HU3: eliminar. Si el producto tiene ventas o pedidos, la llave foránea
     * de la BD lo impide y el controlador muestra el mensaje de error */
    @Transactional
    public void delete(Integer idProducto) {
        productoRepository.deleteById(idProducto);
        productoRepository.flush();
    }

    /* HU8: actualización de stock cuando se reponen productos */
    @Transactional
    public void reponer(Integer idProducto, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("producto.error.cantidad");
        }
        var producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("producto.error.noExiste"));
        producto.setExistencias(producto.getExistencias() + cantidad);
        productoRepository.save(producto);
    }
}
