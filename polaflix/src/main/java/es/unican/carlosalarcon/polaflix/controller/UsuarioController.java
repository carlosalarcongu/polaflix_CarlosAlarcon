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
    @Operation(summary = "Marcar capítulo como visto", description = "Registra que un usuario ha visto un capítulo.")
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
}