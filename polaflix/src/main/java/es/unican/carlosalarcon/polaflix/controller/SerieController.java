package es.unican.carlosalarcon.polaflix.controller;

import es.unican.carlosalarcon.polaflix.domain.Serie;
import es.unican.carlosalarcon.polaflix.domain.Views;
import es.unican.carlosalarcon.polaflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    // =========================================================================
    // GET /series - Devuelve el catálogo resumido (Soporta filtrado opcional)
    // ÚNICO MÉTODO PARA LA RAÍZ /series
    // =========================================================================
    @GetMapping
    @JsonView(Views.SerieResumida.class)
    public ResponseEntity<List<Serie>> obtenerCatalogo(
            @RequestParam(required = false) String inicial,
            @RequestParam(required = false) String titulo) {
        
        // Regla de negocio de la API: Solo un filtro a la vez
        if (inicial != null && titulo != null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        List<Serie> catalogo = serieService.obtenerSeries(inicial, titulo);
        
        if (!catalogo.isEmpty()) {
            return ResponseEntity.ok(catalogo); // 200 OK
        } else {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
    }

    // =========================================================================
    // GET /series/{id} - Devuelve la serie con todo lujo de detalles
    // =========================================================================
    @GetMapping("/{id}")
    @JsonView(Views.SerieDetallada.class)
    public ResponseEntity<Serie> obtenerDetalleSerie(@PathVariable("id") String id) {
        Serie serie = serieService.obtenerSeriePorId(id);
        
        if (serie != null) {
            return ResponseEntity.ok(serie); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}