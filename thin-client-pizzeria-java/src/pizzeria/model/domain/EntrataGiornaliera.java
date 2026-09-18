package pizzeria.model.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

// Rappresenta una riga della vista EntrataGiornaliera.
public class EntrataGiornaliera {
    private final LocalDate data;
    private final BigDecimal totale;

    public EntrataGiornaliera(LocalDate data, BigDecimal totale) {
        this.data = data;
        this.totale = totale;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getTotale() {
        return totale;
    }
}
