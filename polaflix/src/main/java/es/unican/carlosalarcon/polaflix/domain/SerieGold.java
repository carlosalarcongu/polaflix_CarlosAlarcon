package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GOLD")
public class SerieGold extends Serie {
    protected SerieGold() {}
    public SerieGold(String id, String titulo, String sinopsis) { super(id, titulo, sinopsis); }
    @Override public double getCosteVisionado() { return 1.50; }
}