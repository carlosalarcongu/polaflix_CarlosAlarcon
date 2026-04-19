package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonView(Views.UsuarioBasico.class)
    private String username;
    private String contrasenha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Factura> facturas = new HashSet<>();
    
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

    public Usuario(String username, String contrasenha, IBAN iban, PlanSuscripcion planSuscripcion) {
        this.username = username;
        this.contrasenha = contrasenha;
        this.iban = iban;
        this.planSuscripcion = planSuscripcion;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public PlanSuscripcion getPlanSuscripcion() { return planSuscripcion; }

    
    public void agregarSeriePendiente(Serie serie) {
        if (!this.estadoSeries.containsKey(serie)) {
            this.estadoSeries.put(serie, EstadoSerie.PENDIENTE);
        }
    }

    public void verCapitulo(Capitulo capitulo, Factura facturaActual) {
        Serie serie = capitulo.getTemporada().getSerie();
        
        this.capitulosVistos.add(capitulo);

        // Solo actualiz si el capítulo es "más avanzado"
        Capitulo ultimoVisto = this.ultimoCapituloVisto.get(serie);
        if (ultimoVisto == null) {
            this.ultimoCapituloVisto.put(serie, capitulo);
        } else {
            int tempNuevo = capitulo.getTemporada().getNumero();
            int capNuevo = capitulo.getNumero();
            int tempViejo = ultimoVisto.getTemporada().getNumero();
            int capViejo = ultimoVisto.getNumero();

            
            if (tempNuevo > tempViejo || (tempNuevo == tempViejo && capNuevo > capViejo)) {
                this.ultimoCapituloVisto.put(serie, capitulo);
            }
        }

        if (serie.esUltimoCapitulo(capitulo)) {
            this.estadoSeries.put(serie, EstadoSerie.TERMINADA);
        } else {
            this.estadoSeries.put(serie, EstadoSerie.EMPEZADA);
        }

        
        if (!this.planSuscripcion.isTarifaPlana() && facturaActual != null && serie.getCosteVisionado() > 0) {
            String tempCap = "T" + capitulo.getTemporada().getNumero() + "xC" + capitulo.getNumero();
            facturaActual.anadirCargo(new LineaFactura(LocalDate.now(), serie.getTitulo(), tempCap, serie.getCosteVisionado()));
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

    @JsonProperty("estadoSeries")
    @JsonView(Views.UsuarioBasico.class) 
    public Map<String, EstadoSerie> getEstadoSeriesParaJson() {
        Map<String, EstadoSerie> formatoLimpio = new HashMap<>();
        for (Map.Entry<Serie, EstadoSerie> entrada : estadoSeries.entrySet()) {
            formatoLimpio.put(entrada.getKey().getTitulo(), entrada.getValue());
        }
        return formatoLimpio;
    }
}