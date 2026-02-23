package com.polaflix.domain;

import java.util.Objects;

public class Capitulo {
    private final int numero;
    private String titulo;
    private String descripcion;

    public Capitulo(int numero, String titulo, String descripcion) {
        this.numero = numero;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getNumero() { return numero; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capitulo capitulo = (Capitulo) o;
        return numero == capitulo.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}