package es.unican.carlosalarcon.polaflix.controller;

import es.unican.carlosalarcon.polaflix.domain.Serie;
import es.unican.carlosalarcon.polaflix.domain.Views;
import es.unican.carlosalarcon.polaflix.service.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

@RestController
@RequestMapping("/series")
@Tag(name = "Series", description = "API para la gestión y consulta del catálogo de series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    @JsonView(Views.SerieResumida.class)
    @Operation(summary = "Obtener catálogo", description = "Devuelve el catálogo de series. Permite filtrar por inicial o por fragmento de título (solo uno a la vez).")
    public ResponseEntity<List<Serie>> obtenerCatalogo(
            @RequestParam(required = false) String inicial,
            @RequestParam(required = false) String titulo) {
        
        boolean tieneInicial = inicial != null && !inicial.trim().isEmpty();
        boolean tieneTitulo = titulo != null && !titulo.trim().isEmpty();
        
        if (tieneInicial && tieneTitulo) {
            return ResponseEntity.badRequest().build(); 
        }

        List<Serie> catalogo = serieService.obtenerSeries(inicial, titulo);
        
        if (!catalogo.isEmpty()) {
            return ResponseEntity.ok(catalogo); 
        } else {
            return ResponseEntity.noContent().build(); 
        }
    }

    @GetMapping("/{id}")
    @JsonView(Views.SerieDetallada.class)
    @Operation(summary = "Obtener detalle de serie", description = "Devuelve los detalles completos de una serie, incluyendo temporadas, capítulos y actores.")
    public ResponseEntity<Serie> obtenerDetalleSerie(@PathVariable("id") Integer id) {
        Serie serie = serieService.obtenerSeriePorId(id);
        
        if (serie != null) {
            return ResponseEntity.ok(serie); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}