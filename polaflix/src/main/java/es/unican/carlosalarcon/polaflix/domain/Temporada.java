package es.unican.carlosalarcon.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity 
@Table(name = "temporadas")
public class Temporada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    private int numero;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "temporada_id")
    private List<Capitulo> capitulos = new ArrayList<>();

    protected Temporada() {}

    public Temporada(int numero) {
        this.numero = numero;
        this.capitulos = new ArrayList<>();
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temporada temporada = (Temporada) o;
        return numero == temporada.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}