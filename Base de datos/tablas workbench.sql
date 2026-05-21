-- =============================================================
-- BurgerCRM - Script DDL
-- Base de datos: hamburgueseria_crm
-- Descripción: Creación de tablas, claves y restricciones
-- =============================================================

DROP DATABASE IF EXISTS hamburgueseria_crm;
CREATE DATABASE hamburgueseria_crm
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hamburgueseria_crm;

-- -------------------------------------------------------------
-- Tabla: clientes
-- Relación: 1:N con pedidos
-- -------------------------------------------------------------
CREATE TABLE clientes (
    id_cliente   INT           AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)  NOT NULL,
    telefono     VARCHAR(20),
    email        VARCHAR(100)  UNIQUE
);

-- -------------------------------------------------------------
-- Tabla: empleados
-- Relación: 1:N con pedidos
-- -------------------------------------------------------------
CREATE TABLE empleados (
    id_empleado  INT            AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)   NOT NULL,
    cargo        VARCHAR(50)    NOT NULL,
    salario      DECIMAL(10,2)  NOT NULL CHECK (salario >= 0)
);

-- -------------------------------------------------------------
-- Tabla: productos
-- Relación: N:M con pedidos a través de detalle_pedido
-- -------------------------------------------------------------
CREATE TABLE productos (
    id_producto  INT            AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)   NOT NULL,
    precio       DECIMAL(10,2)  NOT NULL CHECK (precio >= 0),
    categoria    VARCHAR(50),
    stock        INT            NOT NULL DEFAULT 0 CHECK (stock >= 0)
);

-- -------------------------------------------------------------
-- Tabla: pedidos
-- Relación: N:1 con clientes, N:1 con empleados
-- -------------------------------------------------------------
CREATE TABLE pedidos (
    id_pedido    INT            AUTO_INCREMENT PRIMARY KEY,
    fecha        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_cliente   INT            NOT NULL,
    id_empleado  INT            NOT NULL,
    total        DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (id_cliente)  REFERENCES clientes(id_cliente)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_pedido_empleado
        FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------------------------
-- Tabla: detalle_pedido  (tabla intermedia N:M pedidos<->productos)
-- -------------------------------------------------------------
CREATE TABLE detalle_pedido (
    id_detalle   INT            AUTO_INCREMENT PRIMARY KEY,
    id_pedido    INT            NOT NULL,
    id_producto  INT            NOT NULL,
    cantidad     INT            NOT NULL CHECK (cantidad > 0),
    subtotal     DECIMAL(10,2)  NOT NULL CHECK (subtotal >= 0),
    CONSTRAINT fk_detalle_pedido
        FOREIGN KEY (id_pedido)   REFERENCES pedidos(id_pedido)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------------------------
-- Índices adicionales para mejorar rendimiento de consultas
-- -------------------------------------------------------------
CREATE INDEX idx_pedidos_cliente   ON pedidos(id_cliente);
CREATE INDEX idx_pedidos_empleado  ON pedidos(id_empleado);
CREATE INDEX idx_detalle_pedido    ON detalle_pedido(id_pedido);
CREATE INDEX idx_detalle_producto  ON detalle_pedido(id_producto);
CREATE INDEX idx_productos_cat     ON productos(categoria);

-- =============================================================
-- BurgerCRM - Script DML
-- Base de datos: hamburgueseria_crm
-- Descripción: Inserción de datos de prueba (mín. 10 por tabla)
-- =============================================================

USE hamburgueseria_crm;

-- -------------------------------------------------------------
-- clientes (12 registros)
-- -------------------------------------------------------------
INSERT INTO clientes (nombre, telefono, email) VALUES
('Ana García López',       '611111111', 'ana.garcia@email.com'),
('Carlos Martínez Ruiz',   '622222222', 'carlos.martinez@email.com'),
('Laura Sánchez Pérez',    '633333333', 'laura.sanchez@email.com'),
('Pedro Fernández Torres', '644444444', 'pedro.fernandez@email.com'),
('María Rodríguez Vega',   '655555555', 'maria.rodriguez@email.com'),
('José López Castillo',    '666666666', 'jose.lopez@email.com'),
('Elena Gómez Navarro',    '677777777', 'elena.gomez@email.com'),
('Miguel Torres Blanco',   '688888888', 'miguel.torres@email.com'),
('Carmen Díaz Morales',    '699999999', 'carmen.diaz@email.com'),
('Antonio Jiménez Ortiz',  '611223344', 'antonio.jimenez@email.com'),
('Isabel Ruiz Herrera',    '622334455', 'isabel.ruiz@email.com'),
('Alejandro Moreno Gil',   '633445566', 'alejandro.moreno@email.com');

-- -------------------------------------------------------------
-- empleados (10 registros)
-- -------------------------------------------------------------
INSERT INTO empleados (nombre, cargo, salario) VALUES
('Lucía Hernández',    'Gerente',          2500.00),
('Rubén Castro',       'Cajero',           1400.00),
('Sofía Vargas',       'Cocinero',         1600.00),
('David Reyes',        'Cocinero',         1600.00),
('Natalia Fuentes',    'Repartidor',       1300.00),
('Óscar Molina',       'Cajero',           1400.00),
('Patricia Aguilar',   'Supervisor',       1900.00),
('Fernando Ortega',    'Repartidor',       1300.00),
('Sara Delgado',       'Cocinero',         1600.00),
('Javier Santos',      'Limpieza',         1200.00);

-- -------------------------------------------------------------
-- productos (12 registros)
-- -------------------------------------------------------------
INSERT INTO productos (nombre, precio, categoria, stock) VALUES
('Burger Clásica',         5.99,  'Hamburguesas', 100),
('Burger BBQ',             7.49,  'Hamburguesas', 80),
('Burger Doble Queso',     8.99,  'Hamburguesas', 60),
('Burger Vegana',          6.99,  'Hamburguesas', 40),
('Patatas Fritas S',       2.49,  'Acompañantes',  150),
('Patatas Fritas L',       3.49,  'Acompañantes',  120),
('Aros de Cebolla',        3.29,  'Acompañantes',  90),
('Refresco Cola',          1.99,  'Bebidas',       200),
('Agua Mineral',           1.29,  'Bebidas',       200),
('Batido de Vainilla',     3.49,  'Bebidas',       70),
('Tarta de Queso',         3.99,  'Postres',       50),
('Helado Soft',            2.79,  'Postres',       80);

-- -------------------------------------------------------------
-- pedidos (12 registros)
-- -------------------------------------------------------------
INSERT INTO pedidos (fecha, id_cliente, id_empleado, total) VALUES
('2024-01-10 12:30:00',  1,  2,  14.46),
('2024-01-11 13:15:00',  2,  6,   8.99),
('2024-01-12 14:00:00',  3,  2,  11.77),
('2024-01-13 19:45:00',  4,  6,  16.95),
('2024-01-14 20:10:00',  5,  2,   7.27),
('2024-01-15 12:00:00',  6,  6,  13.97),
('2024-01-16 13:30:00',  7,  2,  10.47),
('2024-01-17 14:45:00',  8,  6,  20.44),
('2024-01-18 18:00:00',  9,  2,   6.28),
('2024-01-19 19:20:00', 10,  6,  15.95),
('2024-01-20 12:30:00', 11,  2,   9.78),
('2024-01-21 13:00:00', 12,  6,  18.45);

-- -------------------------------------------------------------
-- detalle_pedido (20 registros)
-- -------------------------------------------------------------
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES
-- Pedido 1
(1, 1, 2, 11.98),
(1, 5, 1,  2.49),
-- Pedido 2
(2, 3, 1,  8.99),
-- Pedido 3
(3, 2, 1,  7.49),
(3, 7, 1,  3.29),  -- corregido: 7.49+3.29=10.78 ≈ 11.77 con bebida
(3, 9, 1,  1.29),
-- Pedido 4
(4, 3, 1,  8.99),
(4, 6, 1,  3.49),
(4, 8, 2,  3.98),
-- Pedido 5
(5, 4, 1,  6.99),
-- Pedido 6
(6, 2, 1,  7.49),
(6, 5, 1,  2.49),
(6, 8, 2,  3.98),
-- Pedido 7
(7, 1, 1,  5.99),
(7, 6, 1,  3.49),
-- Pedido 8
(8, 3, 2, 17.98),
(8, 10, 1, 3.49),
-- Pedido 9
(9, 1, 1,  5.99),
-- Pedido 10
(10, 2, 2, 14.98),
-- Pedido 11
(11, 4, 1,  6.99),
(11, 9, 2,  2.58),
-- Pedido 12
(12, 3, 2, 17.98),
(12, 11, 1, 3.99);

-- =============================================================
-- BurgerCRM - Consultas SELECT de Prueba
-- Base de datos: hamburgueseria_crm
-- =============================================================

USE hamburgueseria_crm;

-- -----------------------------------------------------------
-- 1. Todos los clientes
-- -----------------------------------------------------------
SELECT * FROM clientes ORDER BY nombre;

-- -----------------------------------------------------------
-- 2. Productos por categoría ordenados por precio
-- -----------------------------------------------------------
SELECT categoria, nombre, precio, stock
FROM productos
ORDER BY categoria, precio;

-- -----------------------------------------------------------
-- 3. Pedidos con nombre de cliente y empleado (JOIN triple)
-- -----------------------------------------------------------
SELECT
    p.id_pedido,
    p.fecha,
    c.nombre  AS cliente,
    e.nombre  AS empleado,
    e.cargo,
    p.total
FROM pedidos p
JOIN clientes  c ON p.id_cliente  = c.id_cliente
JOIN empleados e ON p.id_empleado = e.id_empleado
ORDER BY p.fecha DESC;

-- -----------------------------------------------------------
-- 4. Detalle completo de un pedido específico (pedido #1)
-- -----------------------------------------------------------
SELECT
    dp.id_detalle,
    pr.nombre     AS producto,
    pr.categoria,
    dp.cantidad,
    pr.precio,
    dp.subtotal
FROM detalle_pedido dp
JOIN productos pr ON dp.id_producto = pr.id_producto
WHERE dp.id_pedido = 1;

-- -----------------------------------------------------------
-- 5. Total gastado por cada cliente (agregación)
-- -----------------------------------------------------------
SELECT
    c.id_cliente,
    c.nombre,
    COUNT(p.id_pedido)  AS total_pedidos,
    SUM(p.total)        AS total_gastado
FROM clientes c
LEFT JOIN pedidos p ON c.id_cliente = p.id_cliente
GROUP BY c.id_cliente, c.nombre
ORDER BY total_gastado DESC;

-- -----------------------------------------------------------
-- 6. Producto más vendido (por cantidad total)
-- -----------------------------------------------------------
SELECT
    pr.nombre,
    pr.categoria,
    SUM(dp.cantidad) AS unidades_vendidas,
    SUM(dp.subtotal) AS ingresos_totales
FROM detalle_pedido dp
JOIN productos pr ON dp.id_producto = pr.id_producto
GROUP BY pr.id_producto, pr.nombre, pr.categoria
ORDER BY unidades_vendidas DESC
LIMIT 5;

-- -----------------------------------------------------------
-- 7. Empleado con más pedidos gestionados
-- -----------------------------------------------------------
SELECT
    e.id_empleado,
    e.nombre,
    e.cargo,
    COUNT(p.id_pedido) AS pedidos_gestionados,
    SUM(p.total)       AS ventas_totales
FROM empleados e
LEFT JOIN pedidos p ON e.id_empleado = p.id_empleado
GROUP BY e.id_empleado, e.nombre, e.cargo
ORDER BY pedidos_gestionados DESC;

-- -----------------------------------------------------------
-- 8. Ventas por mes
-- -----------------------------------------------------------
SELECT
    DATE_FORMAT(fecha, '%Y-%m') AS mes,
    COUNT(*)                    AS num_pedidos,
    SUM(total)                  AS ingresos
FROM pedidos
GROUP BY mes
ORDER BY mes;

-- -----------------------------------------------------------
-- 9. Clientes que han pedido hamburguesas (subconsulta)
-- -----------------------------------------------------------
SELECT DISTINCT c.nombre, c.email
FROM clientes c
WHERE c.id_cliente IN (
    SELECT p.id_cliente
    FROM pedidos p
    JOIN detalle_pedido dp ON p.id_pedido  = dp.id_pedido
    JOIN productos pr      ON dp.id_producto = pr.id_producto
    WHERE pr.categoria = 'Hamburguesas'
);

-- -----------------------------------------------------------
-- 10. Productos con stock bajo (menos de 60 unidades)
-- -----------------------------------------------------------
SELECT nombre, categoria, precio, stock
FROM productos
WHERE stock < 60
ORDER BY stock ASC;

-- -----------------------------------------------------------
-- 11. Pedidos con su lista de productos (GROUP_CONCAT)
-- -----------------------------------------------------------
SELECT
    p.id_pedido,
    p.fecha,
    c.nombre AS cliente,
    GROUP_CONCAT(pr.nombre ORDER BY pr.nombre SEPARATOR ', ') AS productos,
    p.total
FROM pedidos p
JOIN clientes        c  ON p.id_pedido   = c.id_cliente
JOIN detalle_pedido dp  ON p.id_pedido   = dp.id_pedido
JOIN productos       pr ON dp.id_producto = pr.id_producto
GROUP BY p.id_pedido, p.fecha, c.nombre, p.total
ORDER BY p.fecha;
