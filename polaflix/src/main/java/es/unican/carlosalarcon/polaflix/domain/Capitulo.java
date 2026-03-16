package es.unican.carlosalarcon.polaflix.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity 
@Table(name = "capitulos")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private int numero;
    private String titulo;
    private String descripcion;

    protected Capitulo() {}

    public Capitulo(int numero, String titulo, String descripcion) {
        this.numero = numero;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    // Getters necesarios?
    public int getNumero() { return numero; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }

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