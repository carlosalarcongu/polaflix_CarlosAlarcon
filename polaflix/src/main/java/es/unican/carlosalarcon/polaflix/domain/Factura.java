package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

@Entity 
@Table(name = "facturas")
public class Factura {
    
    @Id
    @JsonView(Views.UsuarioBasico.class)
    private String id;
    
    @ManyToOne 
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @JsonView(Views.UsuarioBasico.class)
    private int mes;
    @JsonView(Views.UsuarioBasico.class)
    private int anio;
    
    @ElementCollection
    @JsonView(Views.UsuarioBasico.class)
    private List<LineaFactura> lineas = new ArrayList<>();

    protected Factura() {} 

    public Factura(String id, Usuario usuario, int mes, int anio) {
        this.id = id;
        this.usuario = usuario;
        this.mes = mes;
        this.anio = anio;
    }

    public Usuario getUsuario() { return usuario; }
    public int getMes() { return mes; }
    public int getAnio() { return anio; }
    public String getId() { return id; }

    public void anadirCargo(LineaFactura linea) {
        this.lineas.add(linea);
    }

    @JsonView(Views.UsuarioBasico.class)
    public double getImporteTotal() {
        return lineas.stream().mapToDouble(LineaFactura::getCargo).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}