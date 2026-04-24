package es.unican.carlosalarcon.polaflix.controller;

import es.unican.carlosalarcon.polaflix.domain.Usuario;
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
@Tag(name = "Usuarios", description = "API para las acciones del usuario (perfil, ver capítulos, añadir pendientes)")
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
            @PathVariable("idSerie") String idSerie) {
        try {
            usuarioService.agregarSeriePendiente(username, idSerie);
            return ResponseEntity.ok().build(); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); 
        }
    }
}