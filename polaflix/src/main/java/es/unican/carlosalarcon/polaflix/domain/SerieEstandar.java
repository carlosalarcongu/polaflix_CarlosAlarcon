package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ESTANDAR")
public class SerieEstandar extends Serie {
    protected SerieEstandar() {}
    public SerieEstandar(String id, String titulo, String sinopsis, Persona creador) { super(id, titulo, sinopsis, creador); }
    @Override public double getCosteVisionado() { return 0.50; }
}