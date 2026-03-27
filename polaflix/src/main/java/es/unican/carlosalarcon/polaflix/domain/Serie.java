package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    // CORRECCIÓN: Usar Persona evita duplicidades si alguien es Actor y Creador
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Persona> creadores = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Persona> actores = new HashSet<>();
    
    // CORRECCIÓN: mappedBy establece la relación bidireccional limpia con Temporada
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Temporada> temporadas = new ArrayList<>();
    
    protected Serie() {}

    // CORRECCIÓN: Obligamos a pasar al menos un creador para que no haya series huérfanas
    public Serie(String id, String titulo, String sinopsis, Persona creadorPrincipal) {
        this.id = id;
        this.titulo = titulo;
        this.inicial = titulo.toUpperCase().charAt(0);
        this.sinopsis = sinopsis;
        this.creadores.add(creadorPrincipal);
    }
    
    public abstract double getCosteVisionado();

    public Set<Persona> getActores() { return actores; }
    public Set<Persona> getCreadores() { return creadores; }
    public List<Temporada> getTemporadas() { return temporadas; }
    public String getTitulo() { return titulo; }

    // Método helper para mantener la relación bidireccional
    public void addTemporada(Temporada temporada) {
        this.temporadas.add(temporada);
        temporada.setSerie(this);
    }

    // Lógica inteligente de la entidad
    public boolean esUltimoCapitulo(Capitulo c) {
        if (temporadas.isEmpty()) return false;
        Temporada ultimaTemp = temporadas.get(temporadas.size() - 1);
        if (ultimaTemp.getCapitulos().isEmpty()) return false;
        Capitulo ultimoCap = ultimaTemp.getCapitulos().get(ultimaTemp.getCapitulos().size() - 1);
        return c.equals(ultimoCap);
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