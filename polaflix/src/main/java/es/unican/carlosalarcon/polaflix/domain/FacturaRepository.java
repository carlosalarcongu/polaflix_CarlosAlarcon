package es.unican.carlosalarcon.polaflix.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, String> {
    Factura findByUsernameAndMesAndAnio(String username, int mes, int anio);
    List<Factura> findByUsernameOrderByAnioDescMesDesc(String username);
}