// ===========================
//  UTILIDADES
// ===========================
function escapar(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
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
//  DATOS COMPARTIDOS
// ===========================
const ventas = [
    { id: 1, cliente: "Ana García",    cajero: "Lucía Fernández", fecha: "2025-05-10", total: 24.47, estado: "Completada" },
    { id: 2, cliente: "Pedro Ruiz",    cajero: "Lucía Fernández", fecha: "2025-05-11", total: 13.99, estado: "Completada" },
    { id: 3, cliente: "Marta Sánchez", cajero: "Carlos Martínez", fecha: "2025-05-12", total: 8.99,  estado: "Pendiente"  },
    { id: 4, cliente: "Juan Pérez",    cajero: "Lucía Fernández", fecha: "2025-05-13", total: 31.50, estado: "Completada" },
    { id: 5, cliente: "Sofía Molina",  cajero: "Carlos Martínez", fecha: "2025-05-14", total: 6.48,  estado: "Cancelada"  },
];

const clientes = [
    { id: 1, nombre: "Ana García"    },
    { id: 2, nombre: "Pedro Ruiz"    },
    { id: 3, nombre: "Marta Sánchez" },
    { id: 4, nombre: "Juan Pérez"    },
    { id: 5, nombre: "Sofía Molina"  },
];

const productos = [
    { id: 1, nombre: "Burger Clásica" },
    { id: 2, nombre: "Patatas Deluxe" },
    { id: 3, nombre: "Refresco XL"    },
    { id: 4, nombre: "Burger BBQ"     },
    { id: 5, nombre: "Nuggets x6"     },
    { id: 6, nombre: "Milkshake"      },
];

const usuarios = [
    { id: 1, nombre: "Carlos Martínez", estado: "Activo"   },
    { id: 2, nombre: "Lucía Fernández", estado: "Activo"   },
    { id: 3, nombre: "Miguel Torres",   estado: "Activo"   },
    { id: 4, nombre: "Sara López",      estado: "Inactivo" },
];

// ===========================
//  BADGES
// ===========================
function badgeEstado(estado) {
    const map = {
        "Completada": "badge--green",
        "Pendiente":  "badge--yellow",
        "Cancelada":  "badge--red",
    };
    return `<span class="badge ${map[estado] ?? "badge--gray"}">${escapar(estado)}</span>`;
}

// ===========================
//  TARJETAS KPI
// ===========================
function renderKPIs() {
    const totalVentas    = ventas.reduce((acc, v) => acc + v.total, 0);
    const ventasMes      = ventas.length;
    const clientesTotal  = clientes.length;
    const productosTotal = productos.length;
    const usuariosActivos = usuarios.filter(u => u.estado === "Activo").length;
    const pendientes     = ventas.filter(v => v.estado === "Pendiente").length;

    const kpis = [
        { label: "Ingresos totales",   valor: formatearEuros(totalVentas), icono: "💰" },
        { label: "Ventas este mes",    valor: ventasMes,                   icono: "🧾" },
        { label: "Clientes",           valor: clientesTotal,               icono: "👥" },
        { label: "Productos",          valor: productosTotal,              icono: "🍔" },
        { label: "Usuarios activos",   valor: usuariosActivos,             icono: "👤" },
        { label: "Ventas pendientes",  valor: pendientes,                  icono: "⏳" },
    ];

    const grid = document.getElementById("kpiGrid");
    if (grid) {
        grid.innerHTML = kpis.map(k => `
            <div class="kpi-card">
                <div class="kpi-card__icon">${k.icono}</div>
                <div class="kpi-card__info">
                    <span class="kpi-card__label">${k.label}</span>
                    <span class="kpi-card__valor">${k.valor}</span>
                </div>
            </div>
        `).join("");
    }
}

// ===========================
//  TABLA ÚLTIMAS VENTAS
// ===========================
function renderUltimasVentas() {
    const tbody = document.getElementById("tbodyVentas");
    if (!tbody) return;

    // Mostrar las 5 más recientes (invertidas)
    const recientes = [...ventas].reverse();

    tbody.innerHTML = recientes.map(v => `
        <tr>
            <td>#${String(v.id).padStart(3, "0")}</td>
            <td><strong>${escapar(v.cliente)}</strong></td>
            <td>${escapar(v.cajero)}</td>
            <td>${formatearFecha(v.fecha)}</td>
            <td>${badgeEstado(v.estado)}</td>
            <td><strong>${formatearEuros(v.total)}</strong></td>
        </tr>
    `).join("");
}

// ===========================
//  SIDEBAR MOBILE
// ===========================
const toggleSidebar = () => {
    const sidebar = document.getElementById("sidebar");
    if (sidebar) {
        sidebar.classList.toggle("sidebar--open");
    }
};

// ===========================
//  INIT
// ===========================
document.addEventListener("DOMContentLoaded", () => {
    renderKPIs();
    renderUltimasVentas();
});