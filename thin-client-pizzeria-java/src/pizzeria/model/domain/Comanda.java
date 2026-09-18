package pizzeria.model.domain;

import java.time.LocalDate;

public class Comanda {
    private final LocalDate data;
    private final int numero;

    public Comanda(LocalDate data, int numero) {
        this.data = data;
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public int getNumero() {
        return numero;
    }
}
