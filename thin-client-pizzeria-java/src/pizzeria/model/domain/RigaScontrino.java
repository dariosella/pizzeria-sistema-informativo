package pizzeria.model.domain;

import java.math.BigDecimal;

public class RigaScontrino {
    private final String nomeProdotto;
    private final int quantita;
    private final BigDecimal prezzoUnitario;
    private final BigDecimal subtotale; // Il subtotale viene calcolato dalla query SQL

    public RigaScontrino(String nomeProdotto, int quantita,
                         BigDecimal prezzoUnitario, BigDecimal subtotale) {
        this.nomeProdotto = nomeProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
        this.subtotale = subtotale;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public BigDecimal getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public BigDecimal getSubtotale() {
        return subtotale;
    }
}
