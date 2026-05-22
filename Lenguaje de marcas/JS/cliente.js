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
let clientes = [
    { id: 1, nombre: "Ana García",    email: "ana.garcia@gmail.com",    telefono: "611 234 567", ciudad: "Madrid",    total: 24.47  },
    { id: 2, nombre: "Pedro Ruiz",    email: "pedro.ruiz@hotmail.com",  telefono: "622 345 678", ciudad: "Barcelona", total: 13.99  },
    { id: 3, nombre: "Marta Sánchez", email: "marta.s@gmail.com",       telefono: "633 456 789", ciudad: "Valencia",  total: 8.99   },
    { id: 4, nombre: "Juan Pérez",    email: "juan.perez@outlook.com",  telefono: "644 567 890", ciudad: "Sevilla",   total: 31.50  },
    { id: 5, nombre: "Sofía Molina",  email: "sofia.molina@gmail.com",  telefono: "655 678 901", ciudad: "Madrid",    total: 6.48   },
    { id: 6, nombre: "Luis Torres",   email: "luis.torres@yahoo.com",   telefono: "666 789 012", ciudad: "Bilbao",    total: 45.20  },
    { id: 7, nombre: "Carmen Vega",   email: "carmen.vega@gmail.com",   telefono: "677 890 123", ciudad: "Málaga",    total: 19.75  },
];

let nextId     = 8;
let editandoId = null;

// ===========================
//  RENDER TABLA
// ===========================
function renderTabla(filtro = "") {
    const tbody  = document.getElementById("tablaClientes");
    const buscar = filtro.toLowerCase();

    const filtrados = clientes.filter(c =>
        c.nombre.toLowerCase().includes(buscar)   ||
        c.email.toLowerCase().includes(buscar)    ||
        c.telefono.toLowerCase().includes(buscar) ||
        c.ciudad.toLowerCase().includes(buscar)
    );

    if (filtrados.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; color:var(--color-text-muted); padding:32px;">
                    No se encontraron clientes.
                </td>
            </tr>`;
        return;
    }

    tbody.innerHTML = filtrados.map(c => `
        <tr>
            <td>#${String(c.id).padStart(3, "0")}</td>
            <td><strong>${escapar(c.nombre)}</strong></td>
            <td>${escapar(c.email)}</td>
            <td>${escapar(c.telefono)}</td>
            <td>${escapar(c.ciudad)}</td>
            <td><strong>${formatearEuros(c.total)}</strong></td>
            <td>
                <div class="table__actions">
                    <button class="btn btn--edit btn--sm" onclick="abrirModalEditar(${c.id})">
                        Editar
                    </button>
                    <button class="btn btn--delete btn--sm" onclick="eliminarCliente(${c.id})">
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
    document.getElementById("modalTitle").textContent  = "Nuevo Cliente";
    document.getElementById("inputNombre").value       = "";
    document.getElementById("inputEmail").value        = "";
    document.getElementById("inputTelefono").value     = "";
    document.getElementById("inputCiudad").value       = "";
    document.getElementById("inputTotal").value        = "";
    mostrarModal();
}

function abrirModalEditar(id) {
    const cliente = clientes.find(c => c.id === id);
    if (!cliente) return;

    editandoId = id;
    document.getElementById("modalTitle").textContent  = "Editar Cliente";
    document.getElementById("inputNombre").value       = cliente.nombre;
    document.getElementById("inputEmail").value        = cliente.email;
    document.getElementById("inputTelefono").value     = cliente.telefono;
    document.getElementById("inputCiudad").value       = cliente.ciudad;
    document.getElementById("inputTotal").value        = cliente.total;
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
function guardarCliente() {
    const nombre   = document.getElementById("inputNombre").value.trim();
    const email    = document.getElementById("inputEmail").value.trim();
    const telefono = document.getElementById("inputTelefono").value.trim();
    const ciudad   = document.getElementById("inputCiudad").value.trim();
    const total    = parseFloat(document.getElementById("inputTotal").value);

    if (!nombre || !email || !telefono || !ciudad || isNaN(total) || total < 0) {
        alert("Todos los campos son obligatorios y el total debe ser un número positivo.");
        return;
    }

    if (editandoId === null) {
        clientes.push({ id: nextId++, nombre, email, telefono, ciudad, total });
    } else {
        const idx = clientes.findIndex(c => c.id === editandoId);
        if (idx !== -1) {
            clientes[idx] = { id: editandoId, nombre, email, telefono, ciudad, total };
        }
    }

    cerrarModal();
    renderTabla(document.getElementById("searchInput").value);
}

// ===========================
//  ELIMINAR
// ===========================
function eliminarCliente(id) {
    if (!confirm("¿Seguro que quieres eliminar este cliente?")) return;
    clientes = clientes.filter(c => c.id !== id);
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