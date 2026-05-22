// ===========================
//  UTILIDADES
// ===========================
function escapar(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}

function formatearEuros(valor) {
    return parseFloat(valor).toFixed(2).replace(".", ",") + " €";
}

// ===========================
//  DATOS INICIALES
// ===========================
let productos = [
    { id: 1, nombre: "Burger Clásica",  descripcion: "Hamburguesa con queso y salsa especial", categoria: "Hamburguesa", precio: 8.99,  stock: 24 },
    { id: 2, nombre: "Patatas Deluxe",  descripcion: "Patatas crujientes con salsa BBQ",        categoria: "Complemento", precio: 4.50,  stock: 40 },
    { id: 3, nombre: "Refresco XL",     descripcion: "Bebida fría de gran tamaño",              categoria: "Bebida",      precio: 2.99,  stock: 58 },
    { id: 4, nombre: "Burger BBQ",      descripcion: "Hamburguesa ahumada con bacon crujiente", categoria: "Hamburguesa", precio: 9.99,  stock: 18 },
    { id: 5, nombre: "Aros de Cebolla", descripcion: "Aros crujientes con salsa ranch",         categoria: "Complemento", precio: 3.99,  stock: 32 },
    { id: 6, nombre: "Nuggets x6",      descripcion: "Nuggets de pollo crujientes",             categoria: "Complemento", precio: 5.49,  stock: 45 },
    { id: 7, nombre: "Milkshake",       descripcion: "Batido cremoso de vainilla o chocolate",  categoria: "Bebida",      precio: 4.25,  stock: 20 },
    { id: 8, nombre: "Helado",          descripcion: "Helado suave en barquillo o vaso",        categoria: "Postre",      precio: 3.00,  stock: 30 },
];

let nextId     = 9;
let editandoId = null;

const categorias = ["Hamburguesa", "Complemento", "Bebida", "Postre"];

// ===========================
//  BADGES
// ===========================
function badgeCategoria(cat) {
    const map = {
        "Hamburguesa": "badge--yellow",
        "Complemento": "badge--green",
        "Bebida":      "badge--blue",
        "Postre":      "badge--orange",
    };
    return `<span class="badge ${map[cat] ?? "badge--gray"}">${escapar(cat)}</span>`;
}

// ===========================
//  RENDER TABLA
// ===========================
function renderTabla(filtro = "") {
    const tbody  = document.getElementById("tablaProductos");
    const buscar = filtro.toLowerCase();

    const filtrados = productos.filter(p =>
        p.nombre.toLowerCase().includes(buscar)      ||
        p.descripcion.toLowerCase().includes(buscar) ||
        p.categoria.toLowerCase().includes(buscar)
    );

    if (filtrados.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; color:var(--color-text-muted); padding:32px;">
                    No se encontraron productos.
                </td>
            </tr>`;
        return;
    }

    tbody.innerHTML = filtrados.map(p => `
        <tr>
            <td>#${String(p.id).padStart(3, "0")}</td>
            <td><strong>${escapar(p.nombre)}</strong></td>
            <td>${escapar(p.descripcion)}</td>
            <td>${badgeCategoria(p.categoria)}</td>
            <td><strong>${formatearEuros(p.precio)}</strong></td>
            <td>${p.stock}</td>
            <td>
                <div class="table__actions">
                    <button class="btn btn--edit btn--sm" onclick="abrirModalEditar(${p.id})">
                        Editar
                    </button>
                    <button class="btn btn--delete btn--sm" onclick="eliminarProducto(${p.id})">
                        Eliminar
                    </button>
                </div>
            </td>
        </tr>
    `).join("");
}

// ===========================
//  MODAL
// ===========================
function abrirModalNuevo() {
    editandoId = null;
    document.getElementById("modalTitle").textContent      = "Nuevo Producto";
    document.getElementById("inputNombre").value           = "";
    document.getElementById("inputDescripcion").value      = "";
    document.getElementById("inputCategoria").value        = categorias[0];
    document.getElementById("inputPrecio").value           = "";
    document.getElementById("inputStock").value            = "";
    mostrarModal();
}

function abrirModalEditar(id) {
    const producto = productos.find(p => p.id === id);
    if (!producto) return;

    editandoId = id;
    document.getElementById("modalTitle").textContent      = "Editar Producto";
    document.getElementById("inputNombre").value           = producto.nombre;
    document.getElementById("inputDescripcion").value      = producto.descripcion;
    document.getElementById("inputCategoria").value        = producto.categoria;
    document.getElementById("inputPrecio").value           = producto.precio;
    document.getElementById("inputStock").value            = producto.stock;
    mostrarModal();
}

function mostrarModal() {
    document.getElementById("modalOverlay").classList.add("modal-overlay--visible");
}

function cerrarModal() {
    document.getElementById("modalOverlay").classList.remove("modal-overlay--visible");
}

document.getElementById("modalOverlay").addEventListener("click", function (e) {
    if (e.target === this) cerrarModal();
});

// ===========================
//  GUARDAR (crear / editar)
// ===========================
function guardarProducto() {
    const nombre      = document.getElementById("inputNombre").value.trim();
    const descripcion = document.getElementById("inputDescripcion").value.trim();
    const categoria   = document.getElementById("inputCategoria").value;
    const precio      = parseFloat(document.getElementById("inputPrecio").value);
    const stock       = parseInt(document.getElementById("inputStock").value);

    if (!nombre || !descripcion || isNaN(precio) || precio < 0 || isNaN(stock) || stock < 0) {
        alert("Todos los campos son obligatorios. El precio y el stock deben ser números positivos.");
        return;
    }

    if (editandoId === null) {
        productos.push({ id: nextId++, nombre, descripcion, categoria, precio, stock });
    } else {
        const idx = productos.findIndex(p => p.id === editandoId);
        if (idx !== -1) {
            productos[idx] = { id: editandoId, nombre, descripcion, categoria, precio, stock };
        }
    }

    cerrarModal();
    renderTabla(document.getElementById("searchInput").value);
}

// ===========================
//  ELIMINAR
// ===========================
function eliminarProducto(id) {
    if (!confirm("¿Seguro que quieres eliminar este producto?")) return;
    productos = productos.filter(p => p.id !== id);
    renderTabla(document.getElementById("searchInput").value);
}

// ===========================
//  BUSCADOR EN TIEMPO REAL
// ===========================
document.getElementById("searchInput").addEventListener("input", function () {
    renderTabla(this.value);
});

// ===========================
//  SIDEBAR MOBILE
// ===========================
function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("sidebar--open");
}

// ===========================
//  INIT
// ===========================
renderTabla();