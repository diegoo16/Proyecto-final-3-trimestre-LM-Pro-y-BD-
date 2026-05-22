// ===========================
//  UTILIDADES
// ===========================
function escapar(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}

function fechaHoy() {
    return new Date().toISOString().split("T")[0];
}

function formatearFecha(fecha) {
    if (!fecha) return "—";
    const [y, m, d] = fecha.split("-");
    return `${d}/${m}/${y}`;
}

function formatearEuros(valor) {
    return parseFloat(valor).toFixed(2).replace(".", ",") + " €";
}

// ===========================
//  DATOS INICIALES
// ===========================
let ventas = [
    { id: 1, cliente: "Ana García",      cajero: "Lucía Fernández", fecha: "2025-05-10", total: 24.47, estado: "Completada" },
    { id: 2, cliente: "Pedro Ruiz",      cajero: "Lucía Fernández", fecha: "2025-05-11", total: 13.99, estado: "Completada" },
    { id: 3, cliente: "Marta Sánchez",   cajero: "Carlos Martínez", fecha: "2025-05-12", total: 8.99,  estado: "Pendiente"  },
    { id: 4, cliente: "Juan Pérez",      cajero: "Lucía Fernández", fecha: "2025-05-13", total: 31.50, estado: "Completada" },
    { id: 5, cliente: "Sofía Molina",    cajero: "Carlos Martínez", fecha: "2025-05-14", total: 6.48,  estado: "Cancelada"  },
];

let nextId     = 6;
let editandoId = null;

// ===========================
//  RENDER TABLA
// ===========================
function renderTabla(filtro = "") {
    const tbody  = document.getElementById("tablaVentas");
    const buscar = filtro.toLowerCase();

    const filtradas = ventas.filter(v =>
        v.cliente.toLowerCase().includes(buscar) ||
        v.cajero.toLowerCase().includes(buscar)  ||
        v.estado.toLowerCase().includes(buscar)  ||
        String(v.id).includes(buscar)
    );

    if (filtradas.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; color:var(--color-text-muted); padding:32px;">
                    No se encontraron ventas.
                </td>
            </tr>`;
        return;
    }

    tbody.innerHTML = filtradas.map(v => `
        <tr>
            <td>#${String(v.id).padStart(3, "0")}</td>
            <td><strong>${escapar(v.cliente)}</strong></td>
            <td>${escapar(v.cajero)}</td>
            <td>${formatearFecha(v.fecha)}</td>
            <td><strong>${formatearEuros(v.total)}</strong></td>
            <td>${badgeEstado(v.estado)}</td>
            <td>
                <div class="table__actions">
                    <button class="btn btn--edit btn--sm" onclick="abrirModalEditar(${v.id})">
                        Editar
                    </button>
                    <button class="btn btn--delete btn--sm" onclick="eliminarVenta(${v.id})">
                        Eliminar
                    </button>
                </div>
            </td>
        </tr>
    `).join("");
}

// ===========================
//  BADGES
// ===========================
function badgeEstado(estado) {
    const map = {
        "Completada": "badge--green",
        "Pendiente":  "badge--yellow",
        "Cancelada":  "badge--red",
    };
    const clase = map[estado] ?? "badge--gray";
    return `<span class="badge ${clase}">${escapar(estado)}</span>`;
}

// ===========================
//  MODAL
// ===========================
function abrirModalNuevo() {
    editandoId = null;
    document.getElementById("modalTitle").textContent = "Nueva Venta";
    document.getElementById("inputCliente").value     = "";
    document.getElementById("inputCajero").value      = "";
    document.getElementById("inputFecha").value       = fechaHoy();
    document.getElementById("inputTotal").value       = "";
    document.getElementById("inputEstado").value      = "Completada";
    mostrarModal();
}

function abrirModalEditar(id) {
    const venta = ventas.find(v => v.id === id);
    if (!venta) return;

    editandoId = id;
    document.getElementById("modalTitle").textContent = "Editar Venta";
    document.getElementById("inputCliente").value     = venta.cliente;
    document.getElementById("inputCajero").value      = venta.cajero;
    document.getElementById("inputFecha").value       = venta.fecha;
    document.getElementById("inputTotal").value       = venta.total;
    document.getElementById("inputEstado").value      = venta.estado;
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
function guardarVenta() {
    const cliente = document.getElementById("inputCliente").value.trim();
    const cajero  = document.getElementById("inputCajero").value.trim();
    const fecha   = document.getElementById("inputFecha").value;
    const total   = parseFloat(document.getElementById("inputTotal").value);
    const estado  = document.getElementById("inputEstado").value;

    if (!cliente || !cajero || !fecha || isNaN(total) || total < 0) {
        alert("Todos los campos son obligatorios y el total debe ser un número positivo.");
        return;
    }

    if (editandoId === null) {
        ventas.push({ id: nextId++, cliente, cajero, fecha, total, estado });
    } else {
        const idx = ventas.findIndex(v => v.id === editandoId);
        if (idx !== -1) {
            ventas[idx] = { id: editandoId, cliente, cajero, fecha, total, estado };
        }
    }

    cerrarModal();
    renderTabla(document.getElementById("searchInput").value);
}

// ===========================
//  ELIMINAR
// ===========================
function eliminarVenta(id) {
    if (!confirm("¿Seguro que quieres eliminar esta venta?")) return;
    ventas = ventas.filter(v => v.id !== id);
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