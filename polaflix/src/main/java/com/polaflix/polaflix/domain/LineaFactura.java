package com.polaflix.domain;

import java.time.LocalDate;
import java.util.Objects;

public class LineaFactura {
    private final LocalDate fecha;
    private final String serieNombre;
    private final String temporadaCapitulo;
    private final double cargo;

    public LineaFactura(LocalDate fecha, String serieNombre, String temporadaCapitulo, double cargo) {
        this.fecha = fecha;
        this.serieNombre = serieNombre;
        this.temporadaCapitulo = temporadaCapitulo;
        this.cargo = cargo;
    }

    // Getters ...

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