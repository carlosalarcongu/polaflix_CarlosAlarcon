package com.polaflix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Factura {
    private String id;
    private String username; 
    private int mes;
    private int anio;
    private double importeTotal;
    private List<LineaFactura> lineas;

    public Factura(String id, String username, int mes, int anio) {
        this.id = id;
        this.username = username;
        this.mes = mes;
        this.anio = anio;
        this.importeTotal = 0.0;
        this.lineas = new ArrayList<>();
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