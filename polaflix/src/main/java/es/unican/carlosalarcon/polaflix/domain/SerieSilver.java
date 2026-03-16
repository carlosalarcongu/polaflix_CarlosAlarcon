package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SILVER")
public class SerieSilver extends Serie {
    protected SerieSilver() {}
    public SerieSilver(String id, String titulo, String sinopsis) { super(id, titulo, sinopsis); }
    @Override public double getCosteVisionado() { return 0.75; }
}