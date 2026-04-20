package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categoria_serie", discriminatorType = DiscriminatorType.STRING)
public abstract class Serie {
    
    @Id 
    @JsonView(Views.SerieResumida.class)
    private String id; 
    @JsonView(Views.SerieResumida.class)
    private String titulo;
    @JsonView(Views.SerieResumida.class)
    private char inicial;
    
    @Lob //TODO: Cambiar etiqueta: No usar large object: Consultarlo con Pablo
    @JsonView(Views.SerieResumida.class)
    private String sinopsis;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonView(Views.SerieDetallada.class)
    private Set<Persona> creadores = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonView(Views.SerieDetallada.class)
    private Set<Persona> actores = new HashSet<>();
    
    @JsonManagedReference
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.SerieDetallada.class)
    private List<Temporada> temporadas = new ArrayList<>();
    
    protected Serie() {}

    public Serie(String id, String titulo, String sinopsis, Persona creadorPrincipal) {
        this.id = id;
        this.titulo = titulo;
        this.inicial = titulo.toUpperCase().charAt(0);
        this.sinopsis = sinopsis;
        this.creadores.add(creadorPrincipal);
    }
    
    @JsonProperty("costeVisionado")
    @JsonView(Views.SerieResumida.class)
    public abstract double getCosteVisionado();

    public Set<Persona> getActores() { return actores; }
    public Set<Persona> getCreadores() { return creadores; }
    public List<Temporada> getTemporadas() { return temporadas; }
    public String getTitulo() { return titulo; }

    public void addTemporada(Temporada temporada) {
        this.temporadas.add(temporada);
        temporada.setSerie(this);
    }

    public boolean esUltimoCapitulo(Capitulo c) {
        if (temporadas.isEmpty()) return false;
        Temporada ultimaTemp = temporadas.get(temporadas.size() - 1);
        if (ultimaTemp.getCapitulos().isEmpty()) return false;
        Capitulo ultimoCap = ultimaTemp.getCapitulos().get(ultimaTemp.getCapitulos().size() - 1);
        return c.equals(ultimoCap);
    }

    @JsonProperty("categoria") 
    @JsonView(Views.SerieResumida.class)
    public String getCategoria() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(id, serie.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}