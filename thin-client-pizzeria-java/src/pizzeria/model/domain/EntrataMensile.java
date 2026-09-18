package pizzeria.model.domain;

import java.math.BigDecimal;

// Rappresenta una riga della vista EntrataMensile
public class EntrataMensile {
    private final int anno;
    private final int mese;
    private final BigDecimal totale;

    public EntrataMensile(int anno, int mese, BigDecimal totale) {
        this.anno = anno;
        this.mese = mese;
        this.totale = totale;
    }

    public int getAnno() {
        return anno;
    }

    public int getMese() {
        return mese;
    }

    public BigDecimal getTotale() {
        return totale;
    }
}
