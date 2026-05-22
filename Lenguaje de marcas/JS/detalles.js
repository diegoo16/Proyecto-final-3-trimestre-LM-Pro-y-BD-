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

function formatearFecha(fecha) {
    if (!fecha) return "—";
    const [y, m, d] = fecha.split("-");
    return `${d}/${m}/${y}`;
}

// ===========================
//  DATOS: VENTAS
// ===========================
const ventas = [
    { id: 1, cliente: "Ana García",    cajero: "Lucía Fernández", fecha: "2025-05-10", estado: "Completada" },
    { id: 2, cliente: "Pedro Ruiz",    cajero: "Lucía Fernández", fecha: "2025-05-11", estado: "Completada" },
    { id: 3, cliente: "Marta Sánchez", cajero: "Carlos Martínez", fecha: "2025-05-12", estado: "Pendiente"  },
    { id: 4, cliente: "Juan Pérez",    cajero: "Lucía Fernández", fecha: "2025-05-13", estado: "Completada" },
    { id: 5, cliente: "Sofía Molina",  cajero: "Carlos Martínez", fecha: "2025-05-14", estado: "Cancelada"  },
];

// ===========================
//  DATOS: PRODUCTOS (catálogo)
// ===========================
const catalogo = [
    { nombre: "Burger Clásica",    categoria: "Hamburguesa", precio: 8.99 },
    { nombre: "Burger BBQ",        categoria: "Hamburguesa", precio: 9.99 },
    { nombre: "Burger Doble",      categoria: "Hamburguesa", precio: 11.50 },
    { nombre: "Patatas Deluxe",    categoria: "Complemento", precio: 4.50 },
    { nombre: "Aros de Cebolla",   categoria: "Complemento", precio: 3.99 },
    { nombre: "Nuggets x6",        categoria: "Complemento", precio: 5.49 },
    { nombre: "Refresco XL",       categoria: "Bebida",      precio: 2.99 },
    { nombre: "Agua",              categoria: "Bebida",      precio: 1.50 },
    { nombre: "Milkshake",         categoria: "Bebida",      precio: 4.25 },
    { nombre: "Helado",            categoria: "Postre",      precio: 3.00 },
];

// ===========================
//  DATOS: LÍNEAS POR VENTA
// ===========================
let lineasPorVenta = {
    1: [
        { id: 1, producto: "Burger Clásica",  categoria: "Hamburguesa", cantidad: 2, precio: 8.99  },
        { id: 2, producto: "Patatas Deluxe",  categoria: "Complemento", cantidad: 2, precio: 4.50  },
        { id: 3, producto: "Refresco XL",     categoria: "Bebida",      cantidad: 1, precio: 2.99  },
    ],
    2: [
        { id: 1, producto: "Burger BBQ",      categoria: "Hamburguesa", cantidad: 1, precio: 9.99  },
        { id: 2, producto: "Aros de Cebolla", categoria: "Complemento", cantidad: 1, precio: 3.99  },
    ],
    3: [
        { id: 1, producto: "Burger Clásica",  categoria: "Hamburguesa", cantidad: 1, precio: 8.99  },
    ],
    4: [
        { id: 1, producto: "Burger Doble",    categoria: "Hamburguesa", cantidad: 2, precio: 11.50 },
        { id: 2, producto: "Nuggets x6",      categoria: "Complemento", cantidad: 1, precio: 5.49  },
        { id: 3, producto: "Milkshake",       categoria: "Bebida",      cantidad: 1, precio: 4.25  },
    ],
    5: [
        { id: 1, producto: "Refresco XL",     categoria: "Bebida",      cantidad: 2, precio: 2.99  },
        { id: 2, producto: "Helado",          categoria: "Postre",      cantidad: 1, precio: 3.00  },
    ],
};

let nextLineaId   = 10;
let ventaActualId = null;
let editandoId    = null;

// ===========================
//  INIT: poblar selector
// ===========================
function inicializar() {
    const select = document.getElementById("selectVenta");
    ventas.forEach(v => {
        const opt = document.createElement("option");
        opt.value       = v.id;
        opt.textContent = `#${String(v.id).padStart(3,"0")} · ${v.cliente} · ${formatearFecha(v.fecha)}`;
        select.appendChild(opt);
    });

    // Poblar selector de productos en modal
    const selectProd = document.getElementById("inputProducto");
    catalogo.forEach((p, i) => {
        const opt = document.createElement("option");
        opt.value       = i;
        opt.textContent = `${p.nombre} (${p.categoria})`;
        selectProd.appendChild(opt);
    });
}

// ===========================
//  CARGAR VENTA SELECCIONADA
// ===========================
function cargarVenta() {
    const val = document.getElementById("selectVenta").value;

    if (!val) {
        ventaActualId = null;
        document.getElementById("detalleInfo").style.display = "none";
        document.getElementById("tablaWrap").style.display   = "none";
        document.getElementById("emptyState").style.display  = "flex";
        return;
    }

    ventaActualId = parseInt(val);
    const venta   = ventas.find(v => v.id === ventaActualId);

    // Info cards
    document.getElementById("infoCliente").textContent = venta.cliente;
    document.getElementById("infoCajero").textContent  = venta.cajero;
    document.getElementById("infoFecha").textContent   = formatearFecha(venta.fecha);
    document.getElementById("infoEstado").innerHTML    = badgeEstado(venta.estado);

    document.getElementById("detalleInfo").style.display = "grid";
    document.getElementById("tablaWrap").style.display   = "block";
    document.getElementById("emptyState").style.display  = "none";

    renderLineas();
}

// ===========================
//  RENDER LÍNEAS
// ===========================
function renderLineas() {
    const tbody  = document.getElementById("tablaLineas");
    const lineas = lineasPorVenta[ventaActualId] ?? [];

    if (lineas.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; color:var(--color-text-muted); padding:28px;">
                    Esta venta no tiene líneas. Añade una con el botón.
                </td>
            </tr>`;
        document.getElementById("totalVenta").textContent = "0,00 €";
        return;
    }

    tbody.innerHTML = lineas.map((l, idx) => {
        const subtotal = l.cantidad * l.precio;
        return `
            <tr>
                <td>${idx + 1}</td>
                <td><strong>${escapar(l.producto)}</strong></td>
                <td>${badgeCategoria(l.categoria)}</td>
                <td>${l.cantidad}</td>
                <td>${formatearEuros(l.precio)}</td>
                <td><strong>${formatearEuros(subtotal)}</strong></td>
                <td>
                    <div class="table__actions">
                        <button class="btn btn--edit btn--sm" onclick="abrirModalEditar(${l.id})">
                            Editar
                        </button>
                        <button class="btn btn--delete btn--sm" onclick="eliminarLinea(${l.id})">
                            Eliminar
                        </button>
                    </div>
                </td>
            </tr>`;
    }).join("");

    const total = lineas.reduce((acc, l) => acc + l.cantidad * l.precio, 0);
    document.getElementById("totalVenta").textContent = formatearEuros(total);
}

// ===========================
//  BADGES
// ===========================
function badgeEstado(estado) {
    const map = { "Completada": "badge--green", "Pendiente": "badge--yellow", "Cancelada": "badge--red" };
    return `<span class="badge ${map[estado] ?? "badge--gray"}">${escapar(estado)}</span>`;
}

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
//  MODAL
// ===========================
function abrirModalLinea() {
    if (!ventaActualId) {
        alert("Primero selecciona una venta.");
        return;
    }
    editandoId = null;
    document.getElementById("modalTitle").textContent  = "Añadir Línea";
    document.getElementById("inputProducto").value     = "";
    document.getElementById("inputCantidad").value     = "1";
    document.getElementById("inputPrecio").value       = "";
    document.getElementById("inputSubtotal").value     = "";
    mostrarModal();
}

function abrirModalEditar(id) {
    const lineas = lineasPorVenta[ventaActualId] ?? [];
    const linea  = lineas.find(l => l.id === id);
    if (!linea) return;

    editandoId = id;
    document.getElementById("modalTitle").textContent = "Editar Línea";

    // Seleccionar producto en el combo
    const idx = catalogo.findIndex(p => p.nombre === linea.producto);
    document.getElementById("inputProducto").value = idx >= 0 ? idx : "";
    document.getElementById("inputCantidad").value = linea.cantidad;
    document.getElementById("inputPrecio").value   = linea.precio;
    calcularSubtotal();
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
//  AUTOCOMPLETAR PRECIO
// ===========================
function autocompletarPrecio() {
    const idx = document.getElementById("inputProducto").value;
    if (idx === "") return;
    document.getElementById("inputPrecio").value = catalogo[parseInt(idx)].precio;
    calcularSubtotal();
}

// ===========================
//  CALCULAR SUBTOTAL EN MODAL
// ===========================
function calcularSubtotal() {
    const cantidad = parseFloat(document.getElementById("inputCantidad").value) || 0;
    const precio   = parseFloat(document.getElementById("inputPrecio").value)   || 0;
    document.getElementById("inputSubtotal").value = formatearEuros(cantidad * precio);
}

// ===========================
//  GUARDAR LÍNEA
// ===========================
function guardarLinea() {
    const idxProd  = document.getElementById("inputProducto").value;
    const cantidad = parseInt(document.getElementById("inputCantidad").value);
    const precio   = parseFloat(document.getElementById("inputPrecio").value);

    if (idxProd === "" || isNaN(cantidad) || cantidad < 1 || isNaN(precio) || precio < 0) {
        alert("Selecciona un producto, una cantidad válida y un precio.");
        return;
    }

    const prod = catalogo[parseInt(idxProd)];

    if (!lineasPorVenta[ventaActualId]) {
        lineasPorVenta[ventaActualId] = [];
    }

    if (editandoId === null) {
        lineasPorVenta[ventaActualId].push({
            id: nextLineaId++,
            producto:  prod.nombre,
            categoria: prod.categoria,
            cantidad,
            precio,
        });
    } else {
        const lineas = lineasPorVenta[ventaActualId];
        const idx    = lineas.findIndex(l => l.id === editandoId);
        if (idx !== -1) {
            lineas[idx] = { id: editandoId, producto: prod.nombre, categoria: prod.categoria, cantidad, precio };
        }
    }

    cerrarModal();
    renderLineas();
}

// ===========================
//  ELIMINAR LÍNEA
// ===========================
function eliminarLinea(id) {
    if (!confirm("¿Eliminar esta línea?")) return;
    lineasPorVenta[ventaActualId] = lineasPorVenta[ventaActualId].filter(l => l.id !== id);
    renderLineas();
}

// ===========================
//  SIDEBAR MOBILE
// ===========================
function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("sidebar--open");
}

// ===========================
//  INIT
// ===========================
inicializar();