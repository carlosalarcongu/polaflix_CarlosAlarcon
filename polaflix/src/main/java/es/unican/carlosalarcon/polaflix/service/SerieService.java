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
    // Obtener todo el catálogo (Transacción de solo lectura)
    // =========================================================================
    @Transactional(readOnly = true)
    public List<Serie> obtenerTodasLasSeries() {
        return serieRepository.findAll();
    }

    // =========================================================================
    // Obtener una serie específica por su ID
    // =========================================================================
    @Transactional(readOnly = true)
    public Serie obtenerSeriePorId(String idSerie) {
        return serieRepository.findById(idSerie).orElse(null);
    }
}