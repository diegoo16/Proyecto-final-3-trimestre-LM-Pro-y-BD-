// ===========================
//  UTILIDADES
// ===========================
function escapar(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}

// ===========================
//  DATOS INICIALES
// ===========================
let usuarios = [
    { id: 1, nombre: "Carlos Martínez", email: "carlos@burgercrm.com", rol: "Admin",      estado: "Activo"   },
    { id: 2, nombre: "Lucía Fernández", email: "lucia@burgercrm.com",  rol: "Cajero",     estado: "Activo"   },
    { id: 3, nombre: "Miguel Torres",   email: "miguel@burgercrm.com", rol: "Cocinero",   estado: "Activo"   },
    { id: 4, nombre: "Sara López",      email: "sara@burgercrm.com",   rol: "Repartidor", estado: "Inactivo" },
];

let nextId       = 5;
let editandoId   = null;    // null = nuevo, número = editar

// ===========================
//  RENDER TABLA
// ===========================
function renderTabla(filtro = "") {
    const tbody      = document.getElementById("tablaUsuarios");
    const terminoBus = filtro.toLowerCase();

    const usuariosFiltrados = usuarios.filter(u =>
        u.nombre.toLowerCase().includes(terminoBus) ||
        u.email.toLowerCase().includes(terminoBus)  ||
        u.rol.toLowerCase().includes(terminoBus)     ||
        u.estado.toLowerCase().includes(terminoBus)
    );

    if (usuariosFiltrados.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center; color:var(--color-text-muted); padding:32px;">
                    No se encontraron usuarios.
                </td>
            </tr>`;
        return;
    }

    tbody.innerHTML = usuariosFiltrados.map(u => `
        <tr>
            <td>#${String(u.id).padStart(3, "0")}</td>

            <td><strong>${escapar(u.nombre)}</strong></td>

            <td>${escapar(u.email)}</td>

            <td>${badgeRol(u.rol)}</td>

            <td>${badgeEstado(u.estado)}</td>

            <td>
                <div class="table__actions">
                    <button class="btn btn--edit btn--sm" onclick="abrirModalEditar(${u.id})">
                        Editar
                    </button>
                    <button class="btn btn--delete btn--sm" onclick="eliminarUsuario(${u.id})">
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
function badgeRol(rol) {
    const map = {
        "Admin":      "badge--orange",
        "Cajero":     "badge--blue",
        "Cocinero":   "badge--yellow",
        "Repartidor": "badge--green",
    };
    const clase = map[rol] ?? "badge--gray";
    return `<span class="badge ${clase}">${escapar(rol)}</span>`;
}

function badgeEstado(estado) {
    const clase = estado === "Activo" ? "badge--green" : "badge--gray";
    return `<span class="badge ${clase}">${escapar(estado)}</span>`;
}

// ===========================
//  MODAL
// ===========================
function abrirModalNuevo() {
    editandoId = null;
    document.getElementById("modalTitle").textContent  = "Nuevo Usuario";
    document.getElementById("inputNombre").value       = "";
    document.getElementById("inputEmail").value        = "";
    document.getElementById("inputRol").value          = "Admin";
    document.getElementById("inputEstado").value       = "Activo";
    mostrarModal();
}

function abrirModalEditar(id) {
    const usuario = usuarios.find(u => u.id === id);
    if (!usuario) return;

    editandoId = id;
    document.getElementById("modalTitle").textContent  = "Editar Usuario";
    document.getElementById("inputNombre").value       = usuario.nombre;
    document.getElementById("inputEmail").value        = usuario.email;
    document.getElementById("inputRol").value          = usuario.rol;
    document.getElementById("inputEstado").value       = usuario.estado;
    mostrarModal();
}

function mostrarModal() {
    document.getElementById("modalOverlay").classList.add("modal-overlay--visible");
}

function cerrarModal() {
    document.getElementById("modalOverlay").classList.remove("modal-overlay--visible");
}

// Cerrar modal al pulsar fuera
document.getElementById("modalOverlay").addEventListener("click", function (e) {
    if (e.target === this) cerrarModal();
});

// ===========================
//  GUARDAR (crear / editar)
// ===========================
function guardarUsuario() {
    const nombre = document.getElementById("inputNombre").value.trim();
    const email  = document.getElementById("inputEmail").value.trim();
    const rol    = document.getElementById("inputRol").value;
    const estado = document.getElementById("inputEstado").value;

    if (!nombre || !email) {
        alert("El nombre y el email son obligatorios.");
        return;
    }

    if (editandoId === null) {
        // CREAR
        usuarios.push({ id: nextId++, nombre, email, rol, estado });
    } else {
        // EDITAR
        const idx = usuarios.findIndex(u => u.id === editandoId);
        if (idx !== -1) {
            usuarios[idx] = { id: editandoId, nombre, email, rol, estado };
        }
    }

    cerrarModal();
    renderTabla(document.getElementById("searchInput").value);
}

// ===========================
//  ELIMINAR
// ===========================
function eliminarUsuario(id) {
    if (!confirm("¿Seguro que quieres eliminar este usuario?")) return;
    usuarios = usuarios.filter(u => u.id !== id);
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