/* =========================================================
 * Inventory Manager - rutinas de JavaScript (jQuery)
 * ========================================================= */

/* HU3: coloca el id del producto a eliminar dentro del modal de confirmación */
function confirmarEliminar(id) {
    $('#idEliminar').val(id);
}

/* HU8: coloca el producto en el modal de reponer stock */
function prepararReponer(boton) {
    $('#reponerId').val($(boton).data('id'));
    $('#reponerNombre').text($(boton).data('nombre'));
}

/* HU6: calcula el total de la venta antes de registrarla */
function calcularTotalVenta() {
    var opcion = $('#ventaProducto option:selected');
    var precio = parseFloat(opcion.data('precio')) || 0;
    var stock = parseInt(opcion.data('stock')) || 0;
    var cantidad = parseInt($('#ventaCantidad').val()) || 0;
    $('#ventaCantidad').attr('max', stock > 0 ? stock : null);
    var total = Math.round(precio * cantidad);
    $('#ventaTotal').text('₡' + total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ' '));
}
