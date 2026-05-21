INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo)
VALUES ('Carlos','García López','carlos.garcia@gmail.com','612345678','Madrid','S');

INSERT INTO clientes (nombre, apellidos, email, telefono, ciudad, activo)
VALUES ('María','Rodríguez Pérez','maria.rodriguez@gmail.com','698765432','Madrid','S');

COMMIT;

INSERT INTO usuarios (nombre, apellidos, email, rol, activo)
VALUES ('Pedro','Alonso Vega','pedro.alonso@burgercrm.com','ADMIN','S');

INSERT INTO usuarios (nombre, apellidos, email, rol, activo)
VALUES ('Lucía','Jiménez Castro','lucia.jimenez@burgercrm.com','CAJERO','S');

COMMIT;

INSERT INTO productos
(nombre, descripcion, precio, stock, categoria, activo)
VALUES
('BurgerCRM Clásica','Ternera 200g, lechuga, tomate',8.99,100,'Hamburguesa','S');

INSERT INTO productos
(nombre, descripcion, precio, stock, categoria, activo)
VALUES
('Menú Clásico','Burger + patatas + bebida',12.99,100,'Menú','S');

COMMIT;