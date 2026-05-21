CREATE OR REPLACE PROCEDURE registrar_pedido(
    p_id_cliente     IN NUMBER,
    p_id_usuario     IN NUMBER,
    p_observaciones  IN VARCHAR2,
    p_id_venta       OUT NUMBER
)
IS
    v_precio productos.precio%TYPE;
BEGIN
    SELECT precio
    INTO v_precio
    FROM productos
    WHERE id_producto = 1;

    INSERT INTO ventas (
        id_cliente,
        id_usuario,
        fecha_venta,
        total,
        estado,
        observaciones
    )
    VALUES (
        p_id_cliente,
        p_id_usuario,
        SYSDATE,
        v_precio,
        'COMPLETADA',
        p_observaciones
    )
    RETURNING id_venta INTO p_id_venta;

    INSERT INTO detalle_venta (
        id_venta,
        id_producto,
        cantidad,
        precio_unit
    )
    VALUES (
        p_id_venta,
        1,
        1,
        v_precio
    );

    UPDATE productos
    SET stock = stock - 1
    WHERE id_producto = 1;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Pedido registrado correctamente: ' || p_id_venta);

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END registrar_pedido;
/

DECLARE
  v_id NUMBER;
BEGIN
  registrar_pedido(1,2,'Sin cebolla', v_id);
END;
/

CREATE OR REPLACE PROCEDURE actualizar_stock(
    p_id_producto IN NUMBER,
    p_cantidad IN NUMBER
)
IS
BEGIN
    UPDATE productos
    SET stock = stock + p_cantidad
    WHERE id_producto = p_id_producto;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Stock actualizado');
END;
/

CREATE OR REPLACE FUNCTION calcular_descuento_cliente(
    p_id_cliente IN NUMBER
)
RETURN NUMBER
IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM clientes
    WHERE id_cliente = p_id_cliente;

    IF v_count > 0 THEN
        RETURN 10;
    ELSE
        RETURN 0;
    END IF;
END;
/

CREATE OR REPLACE FUNCTION total_ventas
RETURN NUMBER
IS
  v_total NUMBER;
BEGIN
  SELECT NVL(SUM(total),0)
  INTO v_total
  FROM ventas;

  RETURN v_total;
END;
/
/* Cursores */
DECLARE
  CURSOR c_clientes IS
    SELECT nombre, apellidos FROM clientes;
  v_nombre clientes.nombre%TYPE;
  v_apellidos clientes.apellidos%TYPE;
BEGIN
  OPEN c_clientes;
  LOOP
    FETCH c_clientes INTO v_nombre, v_apellidos;
    EXIT WHEN c_clientes%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(v_nombre || ' ' || v_apellidos);
  END LOOP;
  CLOSE c_clientes;
END;
/

DECLARE
  CURSOR c_productos IS
    SELECT nombre, stock FROM productos;
  v_nombre productos.nombre%TYPE;
  v_stock productos.stock%TYPE;
BEGIN
  OPEN c_productos;
  LOOP
    FETCH c_productos INTO v_nombre, v_stock;
    EXIT WHEN c_productos%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(v_nombre || ' - Stock: ' || v_stock);
  END LOOP;
  CLOSE c_productos;
END;
/

DECLARE
  CURSOR c_usuarios IS
    SELECT nombre, rol FROM usuarios;
  v_nombre usuarios.nombre%TYPE;
  v_rol usuarios.rol%TYPE;
BEGIN
  OPEN c_usuarios;
  LOOP
    FETCH c_usuarios INTO v_nombre, v_rol;
    EXIT WHEN c_usuarios%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(v_nombre || ' - ' || v_rol);
  END LOOP;
  CLOSE c_usuarios;
END;
/

DECLARE
  CURSOR c_ventas IS
    SELECT id_venta, total FROM ventas;
  v_id NUMBER;
  v_total NUMBER;
BEGIN
  OPEN c_ventas;
  LOOP
    FETCH c_ventas INTO v_id, v_total;
    EXIT WHEN c_ventas%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('Venta ' || v_id || ' = ' || v_total);
  END LOOP;
  CLOSE c_ventas;
END;
/

DECLARE
  CURSOR c_detalle IS
    SELECT id_producto, cantidad FROM detalle_venta;
  v_prod NUMBER;
  v_cant NUMBER;
BEGIN
  OPEN c_detalle;
  LOOP
    FETCH c_detalle INTO v_prod, v_cant;
    EXIT WHEN c_detalle%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('Producto ' || v_prod || ' cantidad ' || v_cant);
  END LOOP;
  CLOSE c_detalle;
END;
/