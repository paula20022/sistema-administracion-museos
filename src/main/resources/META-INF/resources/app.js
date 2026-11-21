const API_URL = "http://localhost:8080/api";
let obrasCache = [];


function cambiarVista(vista, boton) {
    document.querySelectorAll(".view-panel").forEach(p => p.classList.remove("active"));
    document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
    document.getElementById("vista-" + vista).classList.add("active");
    if (boton) boton.classList.add("active");
}

document.addEventListener("DOMContentLoaded", () => {
    const opcionesFecha = { year: 'numeric', month: 'long', day: 'numeric' };
    document.getElementById("fecha-actual").textContent = new Date().toLocaleDateString('es-ES', opcionesFecha).toUpperCase();
    sincronizarArchivo();
});

async function sincronizarArchivo() {
    await Promise.all([
        cargarMuseos(),
        cargarCatalogos(),
        cargarObras(),
        cargarHistorial()
    ]);
}


async function cargarMuseos() {
    try {
        const res = await fetch(`${API_URL}/museos`);
        const museos = await res.json();
        document.getElementById("count-museos").textContent = museos.length;

        const tbody = document.getElementById("tabla-museos");
        const selectMuseo = document.getElementById("sala-museo");
        tbody.innerHTML = "";
        selectMuseo.innerHTML = '<option value="">Seleccione institución...</option>';

        museos.forEach(m => {
            tbody.innerHTML += `
                <tr>
                    <td>#${m.id}</td>
                    <td><strong>${m.nombre}</strong><br><small style="color:var(--deer)">${m.descripcion || ""}</small></td>
                    <td>${m.latitud ?? "-"}, ${m.longitud ?? "-"}</td>
                    <td>${m.urlPaginaPrincipal ? `<a href="${m.urlPaginaPrincipal}" target="_blank" style="color:var(--sepia);font-weight:600;">Abrir Portal</a>` : "—"}</td>
                    <td><button class="btn-remove" onclick="eliminarMuseo(${m.id})">Retirar</button></td>
                </tr>`;
            selectMuseo.innerHTML += `<option value="${m.id}">${m.nombre}</option>`;
        });
    } catch (error) {
        console.error("Error cargando museos:", error);
    }
}


async function cargarCatalogos() {
    try {
        const [artistas, categorias, salas] = await Promise.all([
            fetch(`${API_URL}/catalogo/artistas`).then(r => r.json()),
            fetch(`${API_URL}/catalogo/categorias`).then(r => r.json()),
            fetch(`${API_URL}/catalogo/salas`).then(r => r.json())
        ]);

        document.getElementById("count-artistas").textContent = artistas.length;

        poblarSelect("obra-artista", artistas, "Autor anónimo / Sin asignar");
        poblarSelect("obra-categoria", categorias, "Sin clasificar");
        poblarSelect("obra-sala", salas, "En bóveda de reserva");
    } catch (error) {
        console.error("Error cargando catálogos:", error);
    }
}

function poblarSelect(id, items, placeholder) {
    const el = document.getElementById(id);
    el.innerHTML = `<option value="">${placeholder}</option>`;
    items.forEach(item => {
        el.innerHTML += `<option value="${item.id}">${item.nombre}</option>`;
    });
}


async function cargarObras() {
    try {
        const res = await fetch(`${API_URL}/obras`);
        obrasCache = await res.json();
        document.getElementById("count-obras").textContent = obrasCache.length;
        renderizarObras(obrasCache);
    } catch (error) {
        console.error("Error cargando obras:", error);
    }
}

function renderizarObras(lista) {
    const grid = document.getElementById("grid-obras");
    grid.innerHTML = "";

    if (lista.length === 0) {
        grid.innerHTML = `<p style="font-style:italic;color:var(--deer);">No hay piezas catalogadas con ese criterio.</p>`;
        return;
    }

    lista.forEach(obra => {
        const critica = obra.porcentajeConservacion < 60;
        const imgUrl = obra.urlImagen || "https://images.unsplash.com/photo-1578301978693-85fa9c0320b9?auto=format&fit=crop&w=600&q=80";

        grid.innerHTML += `
            <article class="art-frame">
                <div class="canvas-box" onclick="verFichaObra(${obra.id})">
                    <img src="${imgUrl}" alt="${obra.nombre}">
                    <span class="condition-tag ${critica ? 'critical' : ''}">
                        ${obra.porcentajeConservacion}% ${critica ? '• Restaurar' : '• Óptimo'}
                    </span>
                </div>
                <div class="placard">
                    <div>
                        <span class="placard-category">${obra.categoria ? obra.categoria.nombre : "Colección General"}</span>
                        <h4>${obra.nombre}</h4>
                        <p class="placard-meta">Por ${obra.artista ? obra.artista.nombre : "Maestro Anónimo"}</p>
                        <p class="placard-meta">Sala: ${obra.salaActual ? obra.salaActual.nombre : "Bóveda de Reserva"}</p>
                    </div>
                    <div class="placard-actions">
                        <button class="btn-link" onclick="verFichaObra(${obra.id})">Ver Ficha Técnica</button>
                        <button class="btn-remove" onclick="eliminarObra(${obra.id})">Eliminar</button>
                    </div>
                </div>
            </article>`;
    });
}

function filtrarObras() {
    const texto = document.getElementById("buscador-obras").value.toLowerCase();
    const filtradas = obrasCache.filter(o =>
        o.nombre.toLowerCase().includes(texto) ||
        (o.artista && o.artista.nombre.toLowerCase().includes(texto)) ||
        (o.salaActual && o.salaActual.nombre.toLowerCase().includes(texto))
    );
    renderizarObras(filtradas);
}


function verFichaObra(id) {
    const obra = obrasCache.find(o => o.id === id);
    if (!obra) return;

    const imgUrl = obra.urlImagen || "https://images.unsplash.com/photo-1578301978693-85fa9c0320b9?auto=format&fit=crop&w=600&q=80";
    document.getElementById("modal-contenido").innerHTML = `
        <span class="kicker">◆ FICHA TÉCNICA DE ARCHIVO #${obra.id}</span>
        <h2 style="color:var(--space-cadet);font-size:1.85rem;line-height:1.15;margin:0.2rem 0;">${obra.nombre}</h2>
        <p style="font-style:italic;color:var(--sepia);font-size:1.05rem;margin-bottom:0.8rem;">
            ${obra.artista ? obra.artista.nombre + (obra.artista.nacionalidad ? ' (' + obra.artista.nacionalidad + ')' : '') : 'Autor Anónimo'}
        </p>

        <div class="ficha-grid">
            <div class="ficha-img-wrap">
                <img src="${imgUrl}" alt="${obra.nombre}">
            </div>
            <div>
                <p style="font-size:1rem;line-height:1.5;color:var(--bistre-mid);text-align:justify;">
                    ${obra.descripcion || "Sin descripción histórica registrada."}
                </p>
                <div class="ficha-datos">
                    <div>
                        <small style="color:var(--deer);display:block;font-weight:700;">CATEGORÍA</small>
                        <span>${obra.categoria ? obra.categoria.nombre : "General"}</span>
                    </div>
                    <div>
                        <small style="color:var(--deer);display:block;font-weight:700;">SALA ACTUAL</small>
                        <span>${obra.salaActual ? obra.salaActual.nombre : "Bóveda"}</span>
                    </div>
                    <div>
                        <small style="color:var(--deer);display:block;font-weight:700;">CONSERVACIÓN</small>
                        <span>${obra.porcentajeConservacion}%</span>
                    </div>
                </div>
            </div>
        </div>
    `;
    document.getElementById("modal-ficha").classList.add("open");
}

function cerrarModal(e) {
    if (e.target.id === "modal-ficha") cerrarModalDirecto();
}

function cerrarModalDirecto() {
    document.getElementById("modal-ficha").classList.remove("open");
}

document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") cerrarModalDirecto();
});


async function cargarHistorial() {
    try {
        const res = await fetch(`${API_URL}/catalogo/historial`);
        const historial = await res.json();
        const tbody = document.getElementById("tabla-historial");
        tbody.innerHTML = "";

        historial.forEach(h => {
            tbody.innerHTML += `
                <tr>
                    <td><strong>${h.obra ? h.obra.nombre : "Obra"}</strong></td>
                    <td>${h.salaExposicion ? h.salaExposicion.nombre : "Sala"}</td>
                    <td>${h.fechaInicio ? h.fechaInicio.replace("T", " ").substring(0, 16) : "—"}</td>
                </tr>`;
        });
    } catch (error) {
        console.error("Error cargando historial:", error);
    }
}


document.getElementById("form-museo").addEventListener("submit", async (e) => {
    e.preventDefault();
    await fetch(`${API_URL}/museos`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            nombre: document.getElementById("museo-nombre").value,
            descripcion: document.getElementById("museo-desc").value,
            latitud: parseFloat(document.getElementById("museo-lat").value) || null,
            longitud: parseFloat(document.getElementById("museo-lon").value) || null,
            urlPaginaPrincipal: document.getElementById("museo-url").value
        })
    });
    e.target.reset();
    sincronizarArchivo();
});

document.getElementById("form-sala").addEventListener("submit", async (e) => {
    e.preventDefault();
    await fetch(`${API_URL}/catalogo/salas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            museo: { id: parseInt(document.getElementById("sala-museo").value) },
            nombre: document.getElementById("sala-nombre").value,
            descripcion: document.getElementById("sala-desc").value,
            capacidad: parseInt(document.getElementById("sala-capacidad").value)
        })
    });
    e.target.reset();
    cargarCatalogos();
    alert("Sala registrada en el museo.");
});

document.getElementById("form-artista").addEventListener("submit", async (e) => {
    e.preventDefault();
    await fetch(`${API_URL}/catalogo/artistas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            nombre: document.getElementById("artista-nombre").value,
            nacionalidad: document.getElementById("artista-nac").value,
            biografia: document.getElementById("artista-bio").value
        })
    });
    e.target.reset();
    cargarCatalogos();
    alert("Artista inscrito en el archivo.");
});

document.getElementById("form-categoria").addEventListener("submit", async (e) => {
    e.preventDefault();
    await fetch(`${API_URL}/catalogo/categorias`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            nombre: document.getElementById("cat-nombre").value,
            descripcion: document.getElementById("cat-desc").value
        })
    });
    e.target.reset();
    cargarCatalogos();
    alert("Categoría creada.");
});

document.getElementById("form-obra").addEventListener("submit", async (e) => {
    e.preventDefault();
    const artistaId = document.getElementById("obra-artista").value;
    const catId = document.getElementById("obra-categoria").value;
    const salaId = document.getElementById("obra-sala").value;

    await fetch(`${API_URL}/obras`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            nombre: document.getElementById("obra-nombre").value,
            descripcion: document.getElementById("obra-desc").value,
            urlImagen: document.getElementById("obra-img").value,
            porcentajeConservacion: parseInt(document.getElementById("obra-estado").value),
            artista: artistaId ? { id: parseInt(artistaId) } : null,
            categoria: catId ? { id: parseInt(catId) } : null,
            salaActual: salaId ? { id: parseInt(salaId) } : null
        })
    });
    e.target.reset();
    cargarObras();
    cargarHistorial();
});

document.getElementById("form-recorrido").addEventListener("submit", async (e) => {
    e.preventDefault();
    await fetch(`${API_URL}/catalogo/recorridos`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            nombre: document.getElementById("rec-nombre").value,
            descripcion: document.getElementById("rec-desc").value,
            urlPaginaPrincipal: document.getElementById("rec-url").value
        })
    });
    e.target.reset();
    alert("Recorrido guiado publicado.");
});

async function eliminarMuseo(id) {
    await fetch(`${API_URL}/museos/${id}`, { method: "DELETE" });
    sincronizarArchivo();
}

async function eliminarObra(id) {
    await fetch(`${API_URL}/obras/${id}`, { method: "DELETE" });
    cargarObras();
}