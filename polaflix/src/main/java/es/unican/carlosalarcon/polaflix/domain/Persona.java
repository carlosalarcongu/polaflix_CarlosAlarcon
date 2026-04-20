package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.SerieDetallada.class)
    private Long id;

    @JsonView(Views.SerieDetallada.class)
    private String nombre;

    protected Persona() {}

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(nombre, persona.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}