#  BurgerCRM — Sistema de Gestión para Hamburguesería

Proyecto final integrador que implementa un CRM completo para una hamburguesería, abarcando base de datos relacional, backend en Java y frontend web.

---

##  Descripción

BurgerCRM es una aplicación de gestión de clientes y ventas diseñada para una hamburguesería. Permite registrar clientes, empleados y productos, gestionar pedidos y consultar estadísticas desde un dashboard web. El proyecto combina tres capas tecnológicas: base de datos con Oracle y MySQL, lógica de negocio en Java y una interfaz web con HTML/CSS/JS.

---

##  Estructura del proyecto

```
Proyecto-Final/
├── Base de datos/
│   ├── DDL_BurguerCRM.sql          # Esquema de tablas (Oracle)
│   ├── DML_BurguerCRM.sql          # Datos de prueba
│   ├── PLSQL_BurguerCRM.sql        # Procedimientos, funciones y cursores
│   ├── tablas workbench.sql        # Esquema adaptado para MySQL Workbench
│   └── Burger Crm Proyecto.pdf     # Documentación del diseño de BD
│
├── Lenguaje de marcas/
│   ├── dashboard.html              # Panel principal con métricas
│   ├── clientes.html               # Gestión de clientes
│   ├── productos.html              # Catálogo de productos
│   ├── ventas.html                 # Registro de ventas
│   ├── usuarios.html               # Gestión de usuarios
│   ├── detalles.html               # Detalle de pedidos
│   ├── CSS/style.css               # Estilos globales
│   └── JS/                         # Lógica de cada módulo
│       ├── dashboard.js
│       ├── cliente.js
│       ├── productos.js
│       ├── ventas.js
│       ├── usuarios.js
│       └── detalles.js
│
├── Programacion/
│   └── CRM_Burguer/                # Aplicación Java (Maven)
│       └── src/main/java/com/Diego/
│           ├── Main.java
│           ├── controller/         # CustomerController, OrderController, etc.
│           ├── model/              # Customer, Employee, Product, Order, OrderDetail
│           ├── repository/         # Acceso a datos con JDBC
│           ├── service/            # Capa de servicios
│           └── util/               # DatabaseConnection, CsvExporter
│
└── Ingles/
    ├── podcast Diego y Sergio.mp4
    ├── Post ig y facebook.pdf
    └── Tiktok 1.mp4
```

---

##  Tecnologías utilizadas

| Capa | Tecnología |
|---|---|
| Backend | Java 17, Maven |
| Persistencia | JDBC + MySQL (`hamburgueseria_crm`) |
| Base de datos | MySQL Workbench / Oracle SQL |
| PL/SQL | Procedimientos almacenados, funciones, cursores |
| Frontend | HTML5, CSS3, JavaScript (Vanilla) |
| Fuentes | Google Fonts (Bebas Neue, DM Sans) |
| Control de versiones | Git (ramas `main`, `feature/Diego`, `feature/Sergio`) |

---

##  Base de datos

El esquema principal (`hamburgueseria_crm`) contiene las siguientes tablas:

- **clientes** — nombre, teléfono, email
- **empleados** — nombre, cargo, salario
- **productos** — nombre, precio, categoría, stock
- **pedidos** — fecha, cliente, empleado, total
- **detalle_pedido** — relación N:M entre pedidos y productos

### PL/SQL (Oracle)

Se incluyen los siguientes objetos:

- `registrar_pedido` — procedimiento que inserta venta, detalle y descuenta stock en una transacción atómica
- `actualizar_stock` — procedimiento para reposición de inventario
- `calcular_descuento_cliente` — función que retorna el descuento según historial
- `total_ventas` — función que devuelve la suma total de ventas
- Cursores de consulta para clientes, productos, usuarios, ventas y detalles

---

##  Configuración y ejecución

### Requisitos previos

- Java 17+
- Maven 3.x
- MySQL Server (base de datos: `hamburgueseria_crm`)

### 1. Crear la base de datos

Ejecuta el script en MySQL Workbench:

```sql
source tablas workbench.sql
```

O bien el DML para insertar datos de prueba:

```sql
source DML_BurguerCRM.sql
```

### 2. Configurar la conexión

Edita el archivo `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/hamburgueseria_crm?serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña";
```

### 3. Compilar y ejecutar el backend

```bash
cd Programacion/CRM_Burguer
mvn compile
mvn exec:java -Dexec.mainClass="Main"
```

El menú interactivo permite:

```
=== CRM_Burguer ===
1. Add Customer
2. Add Employee
3. Add Product
4. Add Order
5. Add Order Detail
6. Exit
```

### 4. Abrir el frontend

Abre directamente en el navegador el archivo `Lenguaje de marcas/dashboard.html`.

---

##  Módulos del frontend

| Página | Descripción |
|---|---|
| `dashboard.html` | Métricas generales y resumen de actividad |
| `clientes.html` | CRUD de clientes |
| `productos.html` | Catálogo con stock y categorías |
| `ventas.html` | Registro y consulta de ventas |
| `usuarios.html` | Gestión de empleados/usuarios del sistema |
| `detalles.html` | Líneas de detalle de cada pedido |

---

##  Autores

- **Diego** — Backend Java, scripts MySQL, frontend JS, contenido de inglés
- **Sergio** — Apoyo en frontend, contenido de inglés (post redes sociales)

---

##  Notas

- El proyecto fue desarrollado con flujo de ramas Git (`feature/Diego` y `feature/Sergio`) y mergeado a `main`.
- El esquema Oracle (DDL/PL/SQL) y el esquema MySQL (tablas workbench) difieren ligeramente en sintaxis; el backend Java usa exclusivamente MySQL.
- La carpeta `Ingles/` contiene materiales multimedia del proyecto de la asignatura de inglés relacionados con el producto.
