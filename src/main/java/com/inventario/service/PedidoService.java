package com.inventario.service;

import com.inventario.domain.Notificacion;
import com.inventario.domain.Pedido;
import com.inventario.repository.PedidoRepository;
import com.inventario.repository.ProductoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU9 (creación de pedidos), HU13 (estatus) y HU8 (al completar se repone el stock) */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final NotificacionService notificacionService;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository,
            NotificacionService notificacionService) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional(readOnly = true)
    public List<Pedido> getPedidos() {
        return pedidoRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public long getTotalPedidos() {
        return pedidoRepository.count();
    }

    @Transactional(readOnly = true)
    public long getPedidosEnProceso() {
        return pedidoRepository.countByEstado(Pedido.PROCESANDO);
    }

    @Transactional
    public void crear(Integer idProducto, Integer cantidad, String proveedor) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("pedido.error.cantidad");
        }
        if (proveedor == null || proveedor.isBlank()) {
            throw new IllegalArgumentException("pedido.error.proveedor");
        }
        var producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("producto.error.noExiste"));

        var pedido = new Pedido();
        pedido.setProducto(producto);
        pedido.setCantidad(cantidad);
        pedido.setProveedor(proveedor.trim());
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(Pedido.PROCESANDO);
        pedidoRepository.save(pedido);

        notificacionService.crear(Notificacion.PEDIDO_CREADO, producto.getNombre(), cantidad);
    }

    /* Al completar el pedido se suma la cantidad recibida al stock del producto */
    @Transactional
    public void completar(Integer idPedido) {
        var pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("pedido.error.noExiste"));
        if (!Pedido.PROCESANDO.equals(pedido.getEstado())) {
            throw new IllegalArgumentException("pedido.error.estado");
        }
        pedido.setEstado(Pedido.COMPLETADO);
        pedidoRepository.save(pedido);

        var producto = pedido.getProducto();
        producto.setExistencias(producto.getExistencias() + pedido.getCantidad());
        productoRepository.save(producto);

        notificacionService.crear(Notificacion.PEDIDO_COMPLETADO, producto.getNombre(), pedido.getCantidad());
    }

    @Transactional
    public void cancelar(Integer idPedido) {
        var pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("pedido.error.noExiste"));
        if (!Pedido.PROCESANDO.equals(pedido.getEstado())) {
            throw new IllegalArgumentException("pedido.error.estado");
        }
        pedido.setEstado(Pedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
}
