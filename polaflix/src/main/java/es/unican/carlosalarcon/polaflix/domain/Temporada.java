package es.unican.carlosalarcon.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity 
@Table(name = "temporadas")
public class Temporada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; 
    
    private int numero;

    @JsonBackReference
    @ManyToOne
    private Serie serie;

    @JsonManagedReference
    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capitulo> capitulos = new ArrayList<>();

    protected Temporada() {}

    public Temporada(int numero) {
        this.numero = numero;
    }

    public void setSerie(Serie serie) { this.serie = serie; }
    public Serie getSerie() { return serie; }

    public void addCapitulo(Capitulo capitulo) {
        this.capitulos.add(capitulo);
        capitulo.setTemporada(this);
    }

    public List<Capitulo> getCapitulos() { return capitulos; }
    public int getNumero() { return numero; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temporada temporada = (Temporada) o;
        return numero == temporada.numero;
    }

    @Override
    public int hashCode() { return Objects.hash(numero); }
}