package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity 
@Table(name = "facturas")
public class Factura {
    
    @Id
    private String id;
    
    private String username; 
    private int mes;
    private int anio;
    private double importeTotal;
    
    
    @ElementCollection
    private List<LineaFactura> lineas = new ArrayList<>();

    protected Factura() {} 

    public Factura(String id, String username, int mes, int anio) {
        this.id = id;
        this.username = username;
        this.mes = mes;
        this.anio = anio;
        this.importeTotal = 0.0;
    }

    public String getUsername() { return username; }
    public int getMes() { return mes; }
    public int getAnio() { return anio; }
    public String getId() { return id; }

    public void anadirCargo(LineaFactura linea) {
        this.lineas.add(linea);
        this.importeTotal += linea.getCargo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}