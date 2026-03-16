package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria subrogada y autogenerada

    @Column(unique = true, nullable = false)
    private String username; // Identificador natural y único
    private String contrasena;
    
    @Embedded
    private IBAN iban;
    @Embedded
    private PlanSuscripcion planSuscripcion;
    
    // JPA creará tablas auxiliares para guardar estas listas de Strings
    @ElementCollection
    private List<String> seriesPendientes = new ArrayList<>();
    @ElementCollection
    private List<String> seriesEmpezadas = new ArrayList<>();
    @ElementCollection
    private List<String> seriesTerminadas = new ArrayList<>();
    
    // RegistroVisualizacion es un @Embeddable (Value Object)
    @ElementCollection 
    private List<RegistroVisualizacion> historialVisualizaciones = new ArrayList<>();

    protected Usuario() {}

    public Usuario(String username, String contrasena, IBAN iban, PlanSuscripcion planSuscripcion) {
        this.username = username;
        this.contrasena = contrasena;
        this.iban = iban;
        this.planSuscripcion = planSuscripcion;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public PlanSuscripcion getPlanSuscripcion() {
        return planSuscripcion;
    }


    public void agregarSeriePendiente(String serieId) {
        if (!seriesPendientes.contains(serieId) && 
            !seriesEmpezadas.contains(serieId) && 
            !seriesTerminadas.contains(serieId)) {
            
            this.seriesPendientes.add(serieId);
        }
    }

    public void registrarVisualizacionANivelUsuario(String serieId, int numTemporada, int numCapitulo, boolean esUltimoCapituloSerie) {
        
        this.historialVisualizaciones.add(new RegistroVisualizacion(serieId, numTemporada, numCapitulo, LocalDate.now()));

        this.seriesPendientes.remove(serieId); 

        if (!this.seriesEmpezadas.contains(serieId) && !this.seriesTerminadas.contains(serieId)) {
            this.seriesEmpezadas.add(serieId);
        }

        if (esUltimoCapituloSerie) {
            this.seriesEmpezadas.remove(serieId); // La quitamos de empezadas
            if (!this.seriesTerminadas.contains(serieId)) {
                this.seriesTerminadas.add(serieId); // La movemos a terminadas
            }
        }
    }
}