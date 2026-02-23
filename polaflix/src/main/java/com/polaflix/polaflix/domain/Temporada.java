package com.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Temporada {
    private int numero;
    private List<Capitulo> capitulos;

    public Temporada(int numero) {
        this.numero = numero;
        this.capitulos = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temporada temporada = (Temporada) o;
        return numero == temporada.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}