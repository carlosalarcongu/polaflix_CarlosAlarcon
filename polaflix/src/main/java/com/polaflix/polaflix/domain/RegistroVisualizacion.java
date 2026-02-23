package com.polaflix.domain;

import java.time.LocalDate;
import java.util.Objects;

public class RegistroVisualizacion {
    private final String serieId;
    private final int numTemporada;
    private final int numCapitulo;
    private final LocalDate fecha;

    public RegistroVisualizacion(String serieId, int numTemporada, int numCapitulo, LocalDate fecha) {
        this.serieId = serieId;
        this.numTemporada = numTemporada;
        this.numCapitulo = numCapitulo;
        this.fecha = fecha;
    }

    // Getters ...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroVisualizacion that = (RegistroVisualizacion) o;
        return numTemporada == that.numTemporada && 
               numCapitulo == that.numCapitulo && 
               Objects.equals(serieId, that.serieId) && 
               Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serieId, numTemporada, numCapitulo, fecha);
    }
}