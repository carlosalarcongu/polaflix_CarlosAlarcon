package es.unican.carlosalarcon.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity 
@Table(name = "temporadas")
public class Temporada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.SerieDetallada.class)
    private Long id; 


    @JsonView(Views.SerieDetallada.class)
    private String titulo;

    @JsonView(Views.SerieDetallada.class)
    private int numero;

    @JsonView(Views.SerieDetallada.class)
    private String descripcion;

    @JsonBackReference
    @ManyToOne
    private Serie serie;

    @JsonManagedReference
    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.SerieDetallada.class)
    private List<Capitulo> capitulos = new ArrayList<>();

    protected Temporada() {}

    // 1. El constructor se queda solo con el número (como estaba originalmente)
    public Temporada(int numero) {
        this.numero = numero;
    }

    // 2. Modificamos el setter para que genere el título automáticamente cuando se enlaza
    public void setSerie(Serie serie) { 
        this.serie = serie; 
        
        // Ahora sí podemos acceder a serie.getTitulo() porque ya no es null
        if (this.titulo == null) {
            this.titulo = "Temporada " + this.numero + " de " + serie.getTitulo();
        }
        if (this.descripcion == null) {
            this.descripcion = "Descripción pendiente..."; // Un valor por defecto
        }
    }
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
        return Objects.equals(id, temporada.id);
    }

    @Override
    public int hashCode() { 
        return Objects.hash(id); 
    }
}