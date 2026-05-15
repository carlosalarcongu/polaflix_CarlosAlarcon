package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SILVER")
public class SerieSilver extends Serie {
    protected SerieSilver() {}
    public SerieSilver( String titulo, String sinopsis, Persona creador) { super(titulo, sinopsis, creador); }
    @Override public double getCosteVisionado() { return 0.75; }
}