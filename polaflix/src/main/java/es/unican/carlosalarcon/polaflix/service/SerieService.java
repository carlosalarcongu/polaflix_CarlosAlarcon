package es.unican.carlosalarcon.polaflix.service;

import es.unican.carlosalarcon.polaflix.domain.Serie;
import es.unican.carlosalarcon.polaflix.domain.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    // =========================================================================
    // Obtener catálogo (Con soporte para filtrado opcional)
    // =========================================================================
    @Transactional(readOnly = true)
    public List<Serie> obtenerSeries(String inicial, String titulo) {
        if (inicial != null && !inicial.isEmpty()) {
            return serieRepository.findByInicialOrderByTituloAsc(inicial.charAt(0));
        } else if (titulo != null && !titulo.isEmpty()) {
            return serieRepository.findByTituloContainingIgnoreCase(titulo);
        }
        // Si no hay parámetros, devuelve todo
        return serieRepository.findAll();
    }

    // =========================================================================
    // Obtener una serie específica por su ID
    // =========================================================================
    @Transactional(readOnly = true)
    public Serie obtenerSeriePorId(Integer idSerie) {
        return serieRepository.findById(idSerie).orElse(null);
    }
}