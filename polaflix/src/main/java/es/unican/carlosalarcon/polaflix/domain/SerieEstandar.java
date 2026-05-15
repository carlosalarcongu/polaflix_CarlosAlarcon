package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ESTANDAR")
public class SerieEstandar extends Serie {
    protected SerieEstandar() {}
    public SerieEstandar(String titulo, String sinopsis, Persona creador) { super(titulo, sinopsis, creador); }
    @Override public double getCosteVisionado() { return 0.50; }
}