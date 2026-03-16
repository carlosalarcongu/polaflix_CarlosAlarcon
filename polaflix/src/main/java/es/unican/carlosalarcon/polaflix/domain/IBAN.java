package es.unican.carlosalarcon.polaflix.domain;
import jakarta.persistence.*;
import java.util.Objects;
@Embeddable
public class IBAN {
    private final String numeroCuenta;

    public IBAN(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() { return numeroCuenta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBAN iban = (IBAN) o;
        return Objects.equals(numeroCuenta, iban.numeroCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCuenta);
    }
}