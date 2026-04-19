package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@Table(name = "capitulos")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; 

    private int numero;
    private String titulo;
    private String descripcion;
    
    @JsonBackReference
    @ManyToOne
    private Serie serie;

    @JsonBackReference
    @ManyToOne
    private Temporada temporada;

    protected Capitulo() {}

    public Capitulo(int numero, String titulo, String descripcion) {
        this.numero = numero;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public void setTemporada(Temporada temporada) { this.temporada = temporada; }
    public Temporada getTemporada() { return temporada; }

    public int getNumero() { return numero; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capitulo capitulo = (Capitulo) o;
        return numero == capitulo.numero;
    }

    @Override
    public int hashCode() { return Objects.hash(numero); }
}