package es.unican.carlosalarcon.polaflix.domain;

import java.util.Objects;
import jakarta.persistence.*;

@Embeddable
public class PlanSuscripcion {
    private final boolean tarifaPlana;
    private final double cuotaMensual;

    protected PlanSuscripcion() {
        this.tarifaPlana = false;
        this.cuotaMensual = 0.0;
    }

    public PlanSuscripcion(boolean tarifaPlana, double cuotaMensual) {
        this.tarifaPlana = tarifaPlana;
        this.cuotaMensual = cuotaMensual;
    }

    public boolean isTarifaPlana() { return tarifaPlana; }
    public double getCuotaMensual() { return cuotaMensual; }

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