package es.unican.carlosalarcon.polaflix.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, String> {
    List<Serie> findByInicialOrderByTituloAsc(char inicial);
    List<Serie> findByTituloContainingIgnoreCase(String titulo);
}