/* =========================================================
   Inventory Manager - Grupo 26
   Script de la base de datos "inventario"
   Ejecutar completo en MySQL Workbench (ícono del rayo)
   ========================================================= */

/* Para que las tildes y la ñ se guarden correctamente */
SET NAMES utf8mb4;

/* Se elimina la base de datos y el usuario si ya existen */
drop schema if exists inventario;
drop user if exists usuario_inventario;

/* Se crea la base de datos */
CREATE SCHEMA inventario DEFAULT CHARACTER SET utf8mb4;

/* Se crea el usuario de la base de datos */
create user 'usuario_inventario'@'%' identified by 'Inventar1o_Clave.';

/* Se asignan los privilegios sobre la base de datos al usuario */
grant all privileges on inventario.* to 'usuario_inventario'@'%';
flush privileges;

use inventario;

/* HU5: categorías de productos */
create table categoria (
  id_categoria INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(50) NOT NULL,
  activo boolean default true,
  PRIMARY KEY (id_categoria),
  unique (descripcion))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU1, HU2, HU3: productos (relación muchos a uno con categoría) */
create table producto (
  id_producto INT NOT NULL AUTO_INCREMENT,
  id_categoria INT NOT NULL,
  sku VARCHAR(20) NOT NULL,
  nombre VARCHAR(80) NOT NULL,
  precio decimal(12,2) CHECK (precio >= 0),
  existencias int CHECK (existencias >= 0),
  stock_minimo int default 5 CHECK (stock_minimo >= 0),
  activo boolean default true,
  PRIMARY KEY (id_producto),
  unique (sku),
  index ndx_nombre (nombre),
  foreign key fk_producto_categoria (id_categoria) references categoria(id_categoria))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU6: registro de ventas */
create table venta (
  id_venta INT NOT NULL AUTO_INCREMENT,
  id_producto INT NOT NULL,
  fecha datetime NOT NULL,
  cantidad int NOT NULL CHECK (cantidad > 0),
  precio_unitario decimal(12,2) NOT NULL,
  total decimal(12,2) NOT NULL,
  PRIMARY KEY (id_venta),
  foreign key fk_venta_producto (id_producto) references producto(id_producto))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU9 y HU13: pedidos a proveedores y su estado */
create table pedido (
  id_pedido INT NOT NULL AUTO_INCREMENT,
  id_producto INT NOT NULL,
  fecha datetime NOT NULL,
  cantidad int NOT NULL CHECK (cantidad > 0),
  proveedor VARCHAR(80) NOT NULL,
  estado VARCHAR(20) NOT NULL,
  PRIMARY KEY (id_pedido),
  foreign key fk_pedido_producto (id_producto) references producto(id_producto))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU7 y HU10: notificaciones (stock bajo, sin stock, pedidos y mensajes) */
create table notificacion (
  id_notificacion INT NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(30) NOT NULL,
  referencia VARCHAR(100),
  cantidad int,
  fecha datetime NOT NULL,
  leida boolean default false,
  PRIMARY KEY (id_notificacion))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU13 y HU15: mensajes a soporte técnico */
create table mensaje_soporte (
  id_mensaje INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(80) NOT NULL,
  email VARCHAR(100) NOT NULL,
  asunto VARCHAR(100) NOT NULL,
  mensaje text NOT NULL,
  fecha datetime NOT NULL,
  PRIMARY KEY (id_mensaje))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* HU12: configuración del negocio (un solo registro) */
create table configuracion (
  id_configuracion INT NOT NULL AUTO_INCREMENT,
  nombre_negocio VARCHAR(80) NOT NULL,
  email VARCHAR(100),
  telefono VARCHAR(30),
  direccion VARCHAR(200),
  PRIMARY KEY (id_configuracion))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

/* ===================== Datos de prueba ===================== */

INSERT INTO categoria (descripcion, activo) VALUES
('Electrodoméstico', true),
('Tecnología', true);

INSERT INTO producto (id_categoria, sku, nombre, precio, existencias, stock_minimo, activo) VALUES
(1, 'X001', 'Aspiradora Telstar', 48000, 22, 5, true),
(1, 'X002', 'Licuadora Black&Decker', 18000, 0, 5, true),
(2, 'X003', 'Laptop DELL', 285000, 3, 5, true),
(2, 'X004', 'Monitor LG 12”', 85000, 0, 5, true),
(2, 'X005', 'TV Samsung 50”', 495000, 5, 5, true),
(2, 'X006', 'Teléfono Honor Magic 7', 305000, 40, 5, true);

INSERT INTO venta (id_producto, fecha, cantidad, precio_unitario, total) VALUES
(3, '2026-02-12 10:15:00', 1, 285000, 285000),
(6, '2026-02-14 15:40:00', 1, 305000, 305000);

INSERT INTO pedido (id_producto, fecha, cantidad, proveedor, estado) VALUES
(6, '2026-02-12 09:00:00', 25, 'Honor', 'COMPLETADO'),
(5, '2026-02-14 11:30:00', 20, 'Samsung', 'PROCESANDO');

INSERT INTO notificacion (tipo, referencia, cantidad, fecha, leida) VALUES
('STOCK_BAJO', 'Laptop DELL', 3, '2026-02-17 16:59:00', false),
('SIN_STOCK', 'Monitor LG 12”', 0, '2026-02-16 11:59:00', false),
('SIN_STOCK', 'Licuadora Black&Decker', 0, '2026-02-16 10:20:00', false),
('STOCK_BAJO', 'TV Samsung 50”', 5, '2026-02-15 09:10:00', false),
('PEDIDO_CREADO', 'TV Samsung 50”', 20, '2026-02-14 11:30:00', false),
('PEDIDO_COMPLETADO', 'Teléfono Honor Magic 7', 25, '2026-02-13 08:00:00', false);

INSERT INTO configuracion (nombre_negocio, email, telefono, direccion) VALUES
('Inventory Manager', 'contacto@inventorymanager.cr', '2222-2222', 'San José, Costa Rica');
