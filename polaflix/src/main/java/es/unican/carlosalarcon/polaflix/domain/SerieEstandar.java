package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GOLD")
public class SerieEstandar extends Serie {
    protected SerieEstandar() {}
    public SerieEstandar(String id, String titulo, String sinopsis) { super(id, titulo, sinopsis); }
    @Override public double getCosteVisionado() { return 0.50; }
}