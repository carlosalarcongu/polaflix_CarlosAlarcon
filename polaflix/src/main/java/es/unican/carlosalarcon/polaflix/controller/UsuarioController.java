package es.unican.carlosalarcon.polaflix.controller;

import es.unican.carlosalarcon.polaflix.domain.Usuario;
import es.unican.carlosalarcon.polaflix.domain.UsuarioDTO;
import es.unican.carlosalarcon.polaflix.domain.Views;
import es.unican.carlosalarcon.polaflix.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "API para las acciones del usuario (perfil, ver capítulos, añadir/quitar pendientes, registro)")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    @JsonView(Views.UsuarioBasico.class)
    @Operation(summary = "Obtener perfil de usuario", description = "Devuelve los datos del usuario, su plan y el estado de sus series.")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.obtenerUsuario(username);
        
        if (usuario != null) {
            return ResponseEntity.ok(usuario); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/login")
    @JsonView(Views.UsuarioBasico.class)
    @Operation(summary = "Iniciar sesión", description = "Comprueba las credenciales del usuario.")
    public ResponseEntity<Usuario> login(@RequestBody es.unican.carlosalarcon.polaflix.domain.LoginDTO loginDTO) {
        try {
            Usuario usuario = usuarioService.login(loginDTO.getUsername(), loginDTO.getContrasena());
            return ResponseEntity.ok(usuario);
        } catch (SecurityException e) {
            return ResponseEntity.status(401).build();
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{username}")
    @JsonView(Views.UsuarioBasico.class)
    @Operation(summary = "Crear o actualizar usuario", description = "Si el usuario no existe, lo crea (Registro). Si existe, actualiza sus datos mediante un payload JSON.")
    public ResponseEntity<Usuario> guardarOActualizarUsuario(
            @PathVariable("username") String username,
            @RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioActualizado = usuarioService.guardarOActualizarUsuario(
                username, 
                dto.getContrasena(), 
                dto.getIban(), 
                dto.isEsTarifaPlana(), 
                dto.getCuota()
            );
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Borrar usuario", description = "Elimina permanentemente una cuenta de usuario del sistema.")
    public ResponseEntity<Void> borrarUsuario(@PathVariable("username") String username) {
        try {
            usuarioService.borrarUsuario(username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{username}/contrasena")
    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña validando la actual.")
    public ResponseEntity<Void> cambiarContrasena(
            @PathVariable("username") String username,
            @RequestBody es.unican.carlosalarcon.polaflix.domain.CambioContrasenaDTO dto) {
        try {
            usuarioService.cambiarContrasena(username, dto.getActual(), dto.getNueva());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{username}/capitulos-vistos/{idCapitulo}")
    @Operation(summary = "Marcar capítulo como visto", description = "Registra que un usuario ha visto un capítulo y genera cargos en su factura si corresponde.")
    public ResponseEntity<Void> verCapitulo(
            @PathVariable("username") String username,
            @PathVariable("idCapitulo") Long idCapitulo) {
        try {
            usuarioService.verCapitulo(username, idCapitulo);
            return ResponseEntity.ok().build(); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); 
        }
    }

    @PutMapping("/{username}/series-pendientes/{idSerie}")
    @Operation(summary = "Añadir a pendientes", description = "Añade una serie al catálogo personal del usuario en estado PENDIENTE.")
    public ResponseEntity<Void> agregarSeriePendiente(
            @PathVariable("username") String username,
            @PathVariable("idSerie") Integer idSerie) {
        try {
            usuarioService.agregarSeriePendiente(username, idSerie);
            return ResponseEntity.ok().build(); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    @DeleteMapping("/{username}/series-pendientes/{idSerie}")
    @Operation(summary = "Quitar de pendientes", description = "Elimina una serie de la lista del usuario (solo si está en estado PENDIENTE).")
    public ResponseEntity<Void> quitarSeriePendiente(
            @PathVariable("username") String username,
            @PathVariable("idSerie") Integer idSerie) {
        try {
            usuarioService.quitarSeriePendiente(username, idSerie);
            return ResponseEntity.ok().build(); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); 
        }
    }
}