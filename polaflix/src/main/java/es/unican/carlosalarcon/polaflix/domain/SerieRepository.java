package es.unican.carlosalarcon.polaflix.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    List<Serie> findByInicialOrderByTituloAsc(char inicial);
    List<Serie> findByTituloContainingIgnoreCase(String titulo);
    
    // Para forzar la carga de temporadas
    @Query("SELECT s FROM Serie s LEFT JOIN FETCH s.temporadas WHERE s.id = :id")
    Optional<Serie> findSerieConTemporadasPorId(@Param("id") Integer id);

    @Query("SELECT s FROM Serie s JOIN s.temporadas t JOIN t.capitulos c WHERE c.id = :capituloId")
    Optional<Serie> findSerieByCapituloId(@Param("capituloId") Long capituloId);
}