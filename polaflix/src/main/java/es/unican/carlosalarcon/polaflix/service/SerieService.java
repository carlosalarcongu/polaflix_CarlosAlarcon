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

    @Transactional(readOnly = true)
    public List<Serie> obtenerSeries(String inicial, String titulo) {
        if (inicial != null && !inicial.isEmpty()) {
            return serieRepository.findByInicialOrderByTituloAsc(inicial.charAt(0));
        } else if (titulo != null && !titulo.isEmpty()) {
            return serieRepository.findByTituloContainingIgnoreCase(titulo);
        }
        return serieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Serie obtenerSeriePorId(Integer idSerie) {
        Serie serie = serieRepository.findSerieConTemporadasPorId(idSerie).orElse(null);
        if (serie != null) {
            serie.getTemporadas().forEach(t -> t.getCapitulos().size());
        }
        return serie;
    }
}