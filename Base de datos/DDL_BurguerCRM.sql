//Crear Tablas

CREATE TABLE clientes (
  id_cliente NUMBER(10) GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
  nombre VARCHAR2(100) NOT NULL,
  apellidos VARCHAR2(150) NOT NULL,
  email VARCHAR2(200) NOT NULL,
  telefono VARCHAR2(20),
  ciudad VARCHAR2(100),
  fecha_registro DATE DEFAULT SYSDATE NOT NULL,
  activo CHAR(1) DEFAULT 'S' NOT NULL,

  CONSTRAINT pk_clientes PRIMARY KEY (id_cliente),
  CONSTRAINT uq_cli_email UNIQUE (email),
  CONSTRAINT ck_cli_activo CHECK (activo IN ('S','N'))
);

CREATE TABLE usuarios (
  id_usuario NUMBER(10) GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
  nombre VARCHAR2(100) NOT NULL,
  apellidos VARCHAR2(150) NOT NULL,
  email VARCHAR2(200) NOT NULL,
  rol VARCHAR2(50) NOT NULL,
  fecha_alta DATE DEFAULT SYSDATE NOT NULL,
  activo CHAR(1) DEFAULT 'S' NOT NULL,

  CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario),
  CONSTRAINT uq_usu_email UNIQUE (email),
  CONSTRAINT ck_usu_rol CHECK (rol IN ('ADMIN','CAJERO','SUPERVISOR')),
  CONSTRAINT ck_usu_activo CHECK (activo IN ('S','N'))
);

CREATE TABLE productos (
  id_producto NUMBER(10) GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
  nombre VARCHAR2(200) NOT NULL,
  descripcion VARCHAR2(500),
  precio NUMBER(10,2) NOT NULL,
  stock NUMBER(10) DEFAULT 0 NOT NULL,
  categoria VARCHAR2(100),
  activo CHAR(1) DEFAULT 'S' NOT NULL,

  CONSTRAINT pk_productos PRIMARY KEY (id_producto),
  CONSTRAINT ck_prod_precio CHECK (precio > 0),
  CONSTRAINT ck_prod_stock CHECK (stock >= 0),
  CONSTRAINT ck_prod_activo CHECK (activo IN ('S','N'))
);

CREATE TABLE ventas (
  id_venta NUMBER(10) GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
  id_cliente NUMBER(10) NOT NULL,
  id_usuario NUMBER(10) NOT NULL,
  fecha_venta DATE DEFAULT SYSDATE NOT NULL,
  total NUMBER(12,2) DEFAULT 0 NOT NULL,
  estado VARCHAR2(20) DEFAULT 'PENDIENTE' NOT NULL,
  observaciones VARCHAR2(500),

  CONSTRAINT pk_ventas PRIMARY KEY (id_venta),
  CONSTRAINT fk_ven_cliente FOREIGN KEY (id_cliente)
    REFERENCES clientes(id_cliente),
  CONSTRAINT fk_ven_usuario FOREIGN KEY (id_usuario)
    REFERENCES usuarios(id_usuario),
  CONSTRAINT ck_ven_total CHECK (total >= 0),
  CONSTRAINT ck_ven_estado CHECK
    (estado IN ('PENDIENTE','COMPLETADA','CANCELADA'))
);

CREATE TABLE detalle_venta (
  id_detalle NUMBER(10) GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
  id_venta NUMBER(10) NOT NULL,
  id_producto NUMBER(10) NOT NULL,
  cantidad NUMBER(10) NOT NULL,
  precio_unit NUMBER(10,2) NOT NULL,
  subtotal NUMBER(12,2)
    GENERATED ALWAYS AS (cantidad * precio_unit) VIRTUAL,

  CONSTRAINT pk_detalle PRIMARY KEY (id_detalle),
  CONSTRAINT fk_det_venta FOREIGN KEY (id_venta)
    REFERENCES ventas(id_venta) ON DELETE CASCADE,
  CONSTRAINT fk_det_producto FOREIGN KEY (id_producto)
    REFERENCES productos(id_producto),
  CONSTRAINT ck_det_cantidad CHECK (cantidad > 0),
  CONSTRAINT ck_det_precio CHECK (precio_unit > 0)
);

//Crear Indices

CREATE INDEX idx_ventas_cliente ON ventas(id_cliente);
CREATE INDEX idx_ventas_usuario ON ventas(id_usuario);
CREATE INDEX idx_ventas_fecha ON ventas(fecha_venta);
CREATE INDEX idx_det_venta ON detalle_venta(id_venta);
CREATE INDEX idx_det_producto ON detalle_venta(id_producto);
CREATE INDEX idx_productos_cat ON productos(categoria);