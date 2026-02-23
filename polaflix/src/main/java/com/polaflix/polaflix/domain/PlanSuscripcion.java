package com.polaflix.domain;

import java.util.Objects;

public class PlanSuscripcion {
    private final boolean tarifaPlana;
    private final double cuotaMensual;

    public PlanSuscripcion(boolean tarifaPlana, double cuotaMensual) {
        this.tarifaPlana = tarifaPlana;
        this.cuotaMensual = cuotaMensual;
    }

    // Igualdad basada en sus valores (Value Object)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanSuscripcion that = (PlanSuscripcion) o;
        return tarifaPlana == that.tarifaPlana && Double.compare(that.cuotaMensual, cuotaMensual) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarifaPlana, cuotaMensual);
    }
}