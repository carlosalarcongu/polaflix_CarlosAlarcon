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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UsuarioBasico.class)
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonView(Views.UsuarioBasico.class)
    private String username;
    private String contrasenha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonView(Views.UsuarioBasico.class)
    private Set<Factura> facturas = new HashSet<>();
    
    @Embedded
    @JsonView(Views.UsuarioBasico.class)
    private IBAN iban;
    @Embedded
    @JsonView(Views.UsuarioBasico.class)
    private PlanSuscripcion planSuscripcion;
    
    @ElementCollection
    @MapKeyColumn(name = "serie_id")
    @Enumerated(EnumType.STRING)
    private Map<Integer, EstadoSerie> estadoSeries = new HashMap<>();
    
    @ManyToMany
    @JsonView(Views.UsuarioBasico.class)
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    @ManyToMany
    @MapKeyColumn(name = "serie_id")
    private Map<Integer, Capitulo> ultimoCapituloVisto = new HashMap<>();

    @ElementCollection
    @JsonView(Views.UsuarioBasico.class)
    private Set<Integer> seriesArchivadas = new HashSet<>();

    protected Usuario() {}

    public void archivarSerie(Integer serieId) {
        this.seriesArchivadas.add(serieId);
    }

    public void desarchivarSerie(Integer serieId) {
        this.seriesArchivadas.remove(serieId);
    }

    public Usuario(String username, String contrasenha, IBAN iban, PlanSuscripcion planSuscripcion) {
        this.username = username;
        this.contrasenha = contrasenha;
        this.iban = iban;
        this.planSuscripcion = planSuscripcion;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public PlanSuscripcion getPlanSuscripcion() { return planSuscripcion; }
    public String getContrasenha() { return contrasenha; }

    public void actualizarDatos(String nuevaContrasena, IBAN nuevoIban, PlanSuscripcion nuevoPlan) {
        if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
            this.contrasenha = nuevaContrasena;
        }
        if (nuevoIban != null) {
            this.iban = nuevoIban;
        }
        if (nuevoPlan != null) {
            this.planSuscripcion = nuevoPlan;
        }
    }
    
    public void agregarSeriePendiente(Serie serie) {
        if (!this.estadoSeries.containsKey(serie.getId())) {
            this.estadoSeries.put(serie.getId(), EstadoSerie.PENDIENTE);
        }
    }

    public void quitarSeriePendiente(Serie serie) {
        if (EstadoSerie.PENDIENTE.equals(this.estadoSeries.get(serie.getId()))) {
            this.estadoSeries.remove(serie.getId());
        }
    }

    public void verCapitulo(Capitulo capitulo) {
        Serie serie = capitulo.getTemporada().getSerie();
        
        this.capitulosVistos.add(capitulo);

        Capitulo ultimoVisto = this.ultimoCapituloVisto.get(serie.getId());
        if (ultimoVisto == null) {
            this.ultimoCapituloVisto.put(serie.getId(), capitulo);
        } else {
            int tempNuevo = capitulo.getTemporada().getNumero();
            int capNuevo = capitulo.getNumero();
            int tempViejo = ultimoVisto.getTemporada().getNumero();
            int capViejo = ultimoVisto.getNumero();

            if (tempNuevo > tempViejo || (tempNuevo == tempViejo && capNuevo > capViejo)) {
                this.ultimoCapituloVisto.put(serie.getId(), capitulo);
            }
        }

        if (serie.esUltimoCapitulo(capitulo)) {
            this.estadoSeries.put(serie.getId(), EstadoSerie.TERMINADA);
        } else {
            this.estadoSeries.put(serie.getId(), EstadoSerie.EMPEZADA);
        }

        if (!this.planSuscripcion.isTarifaPlana() && serie.getCosteVisionado() > 0) {
            Factura facturaActual = obtenerOCrearFacturaActual();
            String tempCap = "T" + capitulo.getTemporada().getNumero() + "xC" + capitulo.getNumero();
            facturaActual.anadirCargo(new LineaFactura(LocalDate.now(), serie.getTitulo(), tempCap, serie.getCosteVisionado()));
        }
    }

    private Factura obtenerOCrearFacturaActual() {
        int mesActual = LocalDate.now().getMonthValue();
        int anioActual = LocalDate.now().getYear();
        
        for (Factura f : this.facturas) {
            if (f.getMes() == mesActual && f.getAnio() == anioActual) {
                return f;
            }
        }
        
        String idFactura = "F-" + this.username + "-" + mesActual + "-" + anioActual;
        Factura nuevaFactura = new Factura(idFactura, this, mesActual, anioActual);
        this.facturas.add(nuevaFactura);
        return nuevaFactura;
    }

    public boolean comprobarContrasena(String contrasena) {
        return this.contrasenha.equals(contrasena);
    }
    
    @JsonProperty("estadoSeries")
    @JsonView(Views.UsuarioBasico.class) 
    public Map<Integer, EstadoSerie> getEstadoSeriesParaJson() {
        return this.estadoSeries;
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

}