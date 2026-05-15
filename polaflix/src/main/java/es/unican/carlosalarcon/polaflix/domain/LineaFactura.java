package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

@Embeddable 
public class LineaFactura {
    
    @JsonView(Views.UsuarioBasico.class)
    private LocalDate fecha;
    @JsonView(Views.UsuarioBasico.class)
    private String serieNombre;
    @JsonView(Views.UsuarioBasico.class)
    private String temporadaCapitulo;
    @JsonView(Views.UsuarioBasico.class)
    private double cargo;

    @JsonBackReference
    @ManyToOne
    private Factura factura;

    protected LineaFactura() {} 

    public LineaFactura(LocalDate fecha, String serieNombre, String temporadaCapitulo, double cargo) {
        this.fecha = fecha;
        this.serieNombre = serieNombre;
        this.temporadaCapitulo = temporadaCapitulo;
        this.cargo = cargo;
    }

    public double getCargo() { return cargo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaFactura that = (LineaFactura) o;
        return Double.compare(that.cargo, cargo) == 0 && 
               Objects.equals(fecha, that.fecha) && 
               Objects.equals(serieNombre, that.serieNombre) && 
               Objects.equals(temporadaCapitulo, that.temporadaCapitulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, serieNombre, temporadaCapitulo, cargo);
    }
}