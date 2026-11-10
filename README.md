# Inventory Manager – Grupo 26

Sistema web de control de inventario para un local comercial.
Curso **Desarrollo de Aplicaciones Web y Patrones** – Universidad Fidélitas – III Cuatrimestre 2026.

**Integrantes:** Aaron Mauricio Flores Redondo · Melvin Ariel Rojas Hernández
**Prototipo UI:** [Figma – Grupo 26](https://www.figma.com/design/3Xgthr3AhlcguEAR1UYLMH/Grupo-26-Desarrollo-Web)

## Tecnologías (semanas 1 a 8 del curso)

| Tecnología | Uso en el proyecto |
|---|---|
| Java 17 + Spring Boot 3.5.5 | Aplicación web (patrón MVC: domain → repository → service → controller) |
| Thymeleaf | Vistas HTML con fragmentos y plantillas (`templates/general/fragmentos.html`) |
| Bootstrap 5, Font Awesome, jQuery (webjars) | Diseño, íconos y ventanas modales |
| Spring Data JPA / Hibernate | Acceso a datos, asociaciones `@ManyToOne` / `@OneToMany`, consultas derivadas y JPQL |
| MySQL 8 | Base de datos `inventario` (script en `bd/inventario.sql`) |
| Internacionalización | `messages.properties` (español) y `messages_en.properties` (inglés) |
| NetBeans + Maven + Git/GitHub | Desarrollo y control de versiones |

## Historias de usuario implementadas

| HU | Descripción | Dónde |
|---|---|---|
| HU1 | Registro de productos nuevos | Productos → *Añadir producto* |
| HU2 | Edición de productos | Productos → ícono de lápiz |
| HU3 | Eliminar productos (con confirmación) | Productos → ícono de basurero |
| HU4 | Búsqueda de productos (nombre, SKU o categoría) | Barra superior *Buscar* |
| HU5 | Categorías de productos (filtro y administración) | Productos → pestañas / *Categorías* |
| HU6 | Registro de ventas (descuenta stock) | Ventas → *Registrar Venta* |
| HU7 | Alertas de stock bajo (stock ≤ mínimo) | Notificaciones |
| HU8 | Actualización de stock al reponer | Productos → *Reponer stock* y Pedidos → *Completar* |
| HU9 | Creación de pedidos a proveedores | Pedidos → *Nuevo pedido* |
| HU10 | Sección de notificaciones (stock, pedidos y mensajes) | Notificaciones / campana |
| HU12 | Configuración de la información del negocio | Configuración |
| HU13 | Estatus de pedidos (Procesando / Completado / Cancelado) | Pedidos |
| HU13/HU15 | Soporte técnico por mensajes | Soporte / *Ayuda* |
| HU14 | Cambio de idioma (español / inglés) | Selector *Español* de la barra superior |

Pendiente para la entrega final: **HU11 – Administración de usuarios** (requiere seguridad / login, semana 9).

## Cómo ejecutar

1. Abrir **MySQL Workbench**, abrir `bd/inventario.sql` y ejecutarlo completo (ícono del rayo).
   Crea la base `inventario`, el usuario `usuario_inventario` y datos de prueba.
2. Abrir la carpeta del proyecto en **NetBeans** (File → Open Project).
3. Clic derecho al proyecto → **Clean and Build** y luego **Run**.
4. Abrir <http://localhost> en el navegador.
   Si el puerto 80 está ocupado, cambiar `server.port=8080` en `application.properties` y abrir <http://localhost:8080>.

## Estructura

```
src/main/java/com/inventario
├── InventarioApplication.java
├── ProjectConfig.java            (idiomas)
├── domain/        Categoria, Producto, Venta, Pedido, Notificacion, MensajeSoporte, Configuracion
├── repository/    interfaces JpaRepository (consultas derivadas y JPQL)
├── service/       reglas de negocio (@Transactional)
└── controller/    controladores MVC
src/main/resources
├── templates/     vistas Thymeleaf (general/fragmentos.html = plantilla)
├── static/        css, js y tipografías del prototipo
└── messages*.properties
bd/inventario.sql  script de la base de datos
```
