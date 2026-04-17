// --- FUNCIÓN PARA CARGAR EL CATÁLOGO (GET /series) ---
function cargarCatalogo() {
    const contenedor = document.getElementById('catalogoSeries');
    contenedor.innerHTML = "<h3 style='color:yellow;'><blink>Cargando datos desde la base de datos central...</blink></h3>";

    fetch('/series')
        .then(response => {
            if (!response.ok) throw new Error("Error en la red");
            return response.json();
        })
        .then(series => {
            let html = `<table class="retro-table" border="5" bordercolor="magenta" cellpadding="10">
                            <tr>
                                <th>ID</th>
                                <th>TÍTULO</th>
                                <th>CATEGORÍA</th>
                                <th>PRECIO V.D.</th>
                            </tr>`;
            
            series.forEach(s => {
                // Como el DTO manda el tipo con una propiedad o directamente la clase
                let coste = s.costeVisionado > 0 ? s.costeVisionado + " €" : "GRATIS";
                html += `<tr>
                            <td><b>${s.id}</b></td>
                            <td>${s.titulo}</td>
                            <td>⭐</td>
                            <td>${coste}</td>
                         </tr>`;
            });
            html += `</table>`;
            contenedor.innerHTML = html;
        })
        .catch(error => {
            contenedor.innerHTML = `<h2 style='color:red;'>ERROR GENERAL DEL SISTEMA: ${error}</h2>`;
        });
}

// --- FUNCIÓN PARA VER EL PERFIL DE USUARIO (GET /usuarios/{username}) ---
function loginUsuario() {
    const username = document.getElementById('usernameInput').value;
    const divPerfil = document.getElementById('perfilUsuario');

    if(!username) {
        alert("¡Escribe un nombre de usuario primero!");
        return;
    }

    fetch(`/usuarios/${username}`)
        .then(response => {
            if (response.status === 404) {
                throw new Error("Usuario no encontrado en el sistema");
            }
            return response.json();
        })
        .then(usuario => {
            divPerfil.style.display = "block";
            
            let html = `<h2 style="color:lime;">Conectado como: ${usuario.username}</h2>`;
            html += `<p><b>IBAN Registrado:</b> ${usuario.iban.numeroCuenta}</p>`;
            
            let plan = usuario.planSuscripcion.tarifaPlana ? "TARIFA PLANA VIP" : "PAGO POR VISIÓN";
            html += `<p><b>Plan Activo:</b> ${plan}</p>`;
            
            html += `<h3 style="color:cyan;">Tus Series:</h3><ul>`;
            
            // Recorremos el mapa de estadoSeries (Ej: "Juego de Tronos" -> "EMPEZADA")
            for (let [tituloSerie, estado] of Object.entries(usuario.estadoSeries)) {
                html += `<li>${tituloSerie} - <span style="color:yellow;">[${estado}]</span></li>`;
            }
            html += `</ul>`;

            divPerfil.innerHTML = html;
        })
        .catch(error => {
            divPerfil.style.display = "block";
            divPerfil.innerHTML = `<h3 style="color:red;">❌ ${error.message}</h3>`;
        });
}