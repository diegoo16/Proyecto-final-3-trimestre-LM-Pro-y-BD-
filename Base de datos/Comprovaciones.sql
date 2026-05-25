

SET SERVEROUTPUT ON;


SELECT 'Clientes' AS Tabla, COUNT(*) AS Registros FROM clientes;
SELECT 'Usuarios' AS Tabla, COUNT(*) AS Registros FROM usuarios;
SELECT 'Productos' AS Tabla, COUNT(*) AS Registros FROM productos;
SELECT 'Ventas' AS Tabla, COUNT(*) AS Registros FROM ventas;
SELECT 'Detalle_Venta' AS Tabla, COUNT(*) AS Registros FROM detalle_venta;

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo) 
VALUES ('Ana','López','ana.lopez@gmail.com','611223344','Barcelona','S');

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo) 
VALUES ('Luis','Martínez','luis.martinez@hotmail.com','622334455','Valencia','S');

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo) 
VALUES ('Elena','Sánchez','elena.s@gmail.com','633445566','Sevilla','S');

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo) 
VALUES ('Javier','Gómez','javier.gomez@outlook.com','644556677','Málaga','S');

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo) 
VALUES ('Laura','Fernández','laura.f@gmail.com','655667788','Bilbao','S');

COMMIT;


UPDATE clientes SET ciudad = 'Madrid' WHERE id_cliente = 1;
UPDATE clientes SET activo = 'N' WHERE id_cliente = 3;
COMMIT;

DELETE FROM clientes WHERE id_cliente = 5 AND id_cliente NOT IN (SELECT id_cliente FROM ventas);
COMMIT;


DECLARE
    v_id_venta NUMBER;
BEGIN
    registrar_pedido(1, 1, 'Pedido de prueba para comprobación', v_id_venta);
    DBMS_OUTPUT.PUT_LINE('Pedido creado con ID: ' || v_id_venta);
END;
/


SELECT calcular_descuento_cliente(1) AS descuento FROM DUAL;

SELECT total_ventas() AS total_ventas_acumuladas FROM DUAL;


BEGIN
    actualizar_stock(1, 10); 
END;
/


DECLARE
    CURSOR c_clientes IS
        SELECT id_cliente, nombre, apellidos, email 
        FROM clientes 
        WHERE activo = 'S';
    v_id NUMBER;
    v_nombre VARCHAR2(100);
    v_apellidos VARCHAR2(150);
    v_email VARCHAR2(200);
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- CLIENTES ACTIVOS ---');
    OPEN c_clientes;
    LOOP
        FETCH c_clientes INTO v_id, v_nombre, v_apellidos, v_email;
        EXIT WHEN c_clientes%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_id || ' | ' || v_nombre || ' ' || v_apellidos || ' | ' || v_email);
    END LOOP;
    CLOSE c_clientes;
END;
/

