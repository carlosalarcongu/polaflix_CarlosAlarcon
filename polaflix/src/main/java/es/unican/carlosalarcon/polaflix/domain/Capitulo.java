package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity 
@Table(name = "capitulos")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView({Views.SerieDetallada.class, Views.UsuarioBasico.class})
    private Long id; 


    @JsonView({Views.SerieDetallada.class, Views.UsuarioBasico.class})
    private int numero;
    @JsonView({Views.SerieDetallada.class, Views.UsuarioBasico.class})
    private String titulo;
    @JsonView({Views.SerieDetallada.class, Views.UsuarioBasico.class})
    private String descripcion;
    
    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    private Serie serie;

    @JsonBackReference
    @JsonIgnore
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
        return Objects.equals(id, capitulo.id);
    }

    @Override
    public int hashCode() { 
        return Objects.hash(id); 
    }
}