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
@Tag(name = "Usuarios", description = "API simplificada para acceso y visualización")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    @JsonView(Views.UsuarioBasico.class)
    @Operation(summary = "Entrar al sistema", description = "Comprueba si el usuario existe y devuelve sus datos.")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.obtenerUsuario(username);
        if (usuario != null) {
            return ResponseEntity.ok(usuario); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PutMapping("/{username}/capitulos-vistos/{idCapitulo}")
    @Operation(summary = "Marcar capÃ­tulo como visto", description = "Registra que un usuario ha visto un capÃ­tulo.")
    public ResponseEntity<Void> verCapitulo(
            @PathVariable("username") String username,
            @PathVariable("idCapitulo") Long idCapitulo) {
        try {
            usuarioService.verCapitulo(username, idCapitulo);
            return ResponseEntity.ok().build(); 
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); 
        }
    }

    @PutMapping("/{username}/series-archivadas/{idSerie}")
    @Operation(summary = "Archivar serie", description = "Permite a un usuario archivar una serie.")
    public ResponseEntity<Void> archivarSerie(
            @PathVariable("username") String username,
            @PathVariable("idSerie") Integer idSerie) {
        try {
            usuarioService.archivarSerie(username, idSerie);
            return ResponseEntity.ok().build();
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{username}/series-archivadas/{idSerie}")
    @Operation(summary = "Desarchivar serie", description = "Permite a un usuario desarchivar una serie.")
    public ResponseEntity<Void> desarchivarSerie(
            @PathVariable("username") String username,
            @PathVariable("idSerie") Integer idSerie) {
        try {
            usuarioService.desarchivarSerie(username, idSerie);
            return ResponseEntity.ok().build();
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
