package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categoria_serie", discriminatorType = DiscriminatorType.STRING)
public abstract class Serie {
    
    @Id 
    private String id; 
    private String titulo;
    private char inicial;
    
    @Lob
    private String sinopsis;
    
    @ElementCollection
    private List<String> creadores = new ArrayList<>();
    
    @ElementCollection
    private List<String> actores = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "serie_id")
    private List<Temporada> temporadas = new ArrayList<>();
    
    protected Serie() {}

    public Serie(String id, String titulo, String sinopsis) {
        this.id = id;
        this.titulo = titulo;
        this.inicial = titulo.toUpperCase().charAt(0);
        this.sinopsis = sinopsis;
    }
    
    public abstract double getCosteVisionado();

    public List<String> getActores() {
        return actores;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public List<String> getCreadores() {
        return creadores;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(id, serie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}