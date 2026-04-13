package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonView(Views.UsuarioBasico.class)
    private String username;
    private String contrasena;
    
    @Embedded
    @JsonView(Views.UsuarioBasico.class)
    private IBAN iban;
    @Embedded
    @JsonView(Views.UsuarioBasico.class)
    private PlanSuscripcion planSuscripcion;
    
    @ElementCollection
    @MapKeyJoinColumn(name = "serie_id")
    @Enumerated(EnumType.STRING)
    private Map<Serie, EstadoSerie> estadoSeries = new HashMap<>();
    
    @ManyToMany
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    @ManyToMany
    @MapKeyJoinColumn(name = "serie_id")
    private Map<Serie, Capitulo> ultimoCapituloVisto = new HashMap<>();

    protected Usuario() {}

    public Usuario(String username, String contrasena, IBAN iban, PlanSuscripcion planSuscripcion) {
        this.username = username;
        this.contrasena = contrasena;
        this.iban = iban;
        this.planSuscripcion = planSuscripcion;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public PlanSuscripcion getPlanSuscripcion() { return planSuscripcion; }

    // CORRECCIÓN: "Los servicios a nivel de dominio aceptan objetos, no identificadores"
    public void agregarSeriePendiente(Serie serie) {
        if (!this.estadoSeries.containsKey(serie)) {
            this.estadoSeries.put(serie, EstadoSerie.PENDIENTE);
        }
    }

    // CORRECCIÓN: "registrar Visualización debería aceptar un único objeto"
    public void verCapitulo(Capitulo capitulo) {
        Serie serie = capitulo.getTemporada().getSerie();
        
        this.capitulosVistos.add(capitulo);
        this.ultimoCapituloVisto.put(serie, capitulo);

        if (serie.esUltimoCapitulo(capitulo)) {
            this.estadoSeries.put(serie, EstadoSerie.TERMINADA);
        } else {
            this.estadoSeries.put(serie, EstadoSerie.EMPEZADA);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() { return Objects.hash(username); }

    @JsonProperty("estadoSeries") // Le decimos a Jackson que llame a esto "estadoSeries" en el JSON
    @JsonView(Views.UsuarioBasico.class) // Solo se ve en la vista básica
    public Map<String, EstadoSerie> getEstadoSeriesParaJson() {
        Map<String, EstadoSerie> formatoLimpio = new HashMap<>();
        for (Map.Entry<Serie, EstadoSerie> entrada : estadoSeries.entrySet()) {
            formatoLimpio.put(entrada.getKey().getTitulo(), entrada.getValue());
        }
        return formatoLimpio;
    }
}