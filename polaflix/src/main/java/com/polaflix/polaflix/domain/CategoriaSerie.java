package com.polaflix.domain;

import java.util.Objects;

public class CategoriaSerie {
    // "Estandar", "Silver", "Gold"
    private final String tipo; 
    private final double costeVisionado;

    public CategoriaSerie(String tipo, double costeVisionado) {
        this.tipo = tipo;
        this.costeVisionado = costeVisionado;
    }

    public double getCosteVisionado() { return costeVisionado; }
    public String getTipo() { return tipo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaSerie that = (CategoriaSerie) o;
        return Double.compare(that.costeVisionado, costeVisionado) == 0 && 
               Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, costeVisionado);
    }
}