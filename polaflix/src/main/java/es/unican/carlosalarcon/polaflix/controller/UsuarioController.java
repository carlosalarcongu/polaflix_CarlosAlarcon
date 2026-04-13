package es.unican.carlosalarcon.polaflix.controller;

import es.unican.carlosalarcon.polaflix.domain.Usuario;
import es.unican.carlosalarcon.polaflix.domain.Views;
import es.unican.carlosalarcon.polaflix.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // =========================================================================
    // GET /usuarios/{username} - Obtiene el perfil básico del usuario
    // =========================================================================
    @GetMapping("/{username}")
    @JsonView(Views.UsuarioBasico.class)
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.obtenerUsuario(username);
        
        if (usuario != null) {
            return ResponseEntity.ok(usuario); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    // =========================================================================
    // PUT /usuarios/{username}/capitulos-vistos/{idCapitulo} - Ve un capítulo
    // =========================================================================
    @PutMapping("/{username}/capitulos-vistos/{idCapitulo}")
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

    // =========================================================================
    // PUT /usuarios/{username}/series-pendientes/{idSerie} - Añade a pendientes
    // =========================================================================
    @PutMapping("/{username}/series-pendientes/{idSerie}")
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