package com.inventario.service;

import com.inventario.domain.Venta;
import com.inventario.repository.ProductoRepository;
import com.inventario.repository.VentaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU6: Registro de ventas */
@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final NotificacionService notificacionService;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
            NotificacionService notificacionService) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional(readOnly = true)
    public List<Venta> getVentas() {
        return ventaRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public long getTotalVentas() {
        return ventaRepository.count();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalIngresos() {
        return ventaRepository.totalIngresos();
    }

    /* Registra la venta, calcula el total y descuenta el stock del producto */
    @Transactional
    public void registrar(Integer idProducto, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("venta.error.cantidad");
        }
        var producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("producto.error.noExiste"));
        if (cantidad > producto.getExistencias()) {
            throw new IllegalArgumentException("venta.error.stock");
        }

        var venta = new Venta();
        venta.setProducto(producto);
        venta.setFecha(LocalDateTime.now());
        venta.setCantidad(cantidad);
        venta.setPrecioUnitario(producto.getPrecio());
        venta.setTotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        ventaRepository.save(venta);

        producto.setExistencias(producto.getExistencias() - cantidad);
        productoRepository.save(producto);

        /* HU7: si el producto quedó bajo el mínimo se genera la alerta */
        notificacionService.revisarStock(producto);
    }
}
