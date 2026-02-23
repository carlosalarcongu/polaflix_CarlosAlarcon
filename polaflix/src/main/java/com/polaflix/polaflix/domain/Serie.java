package com.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Serie {
    private String id; // Aggregate Root ID
    private String titulo;
    private char inicial;
    private String sinopsis;
    private List<String> creadores;
    private List<String> actores;
    private CategoriaSerie categoria;
    private List<Temporada> temporadas;

    public Serie(String id, String titulo, String sinopsis, CategoriaSerie categoria) {
        this.id = id;
        this.titulo = titulo;
        this.inicial = titulo.toUpperCase().charAt(0);
        this.sinopsis = sinopsis;
        this.categoria = categoria;
        this.creadores = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.temporadas = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(id, serie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}