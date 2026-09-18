package pizzeria.model.domain;

import java.math.BigDecimal;
import java.util.List;

public class Scontrino {
    private final List<RigaScontrino> righe;
    private final BigDecimal totale;

    public Scontrino(List<RigaScontrino> righe, BigDecimal totale) {
        this.righe = righe;
        this.totale = totale;
    }

    public List<RigaScontrino> getRighe() {
        return righe;
    }

    public BigDecimal getTotale() {
        return totale;
    }
}
