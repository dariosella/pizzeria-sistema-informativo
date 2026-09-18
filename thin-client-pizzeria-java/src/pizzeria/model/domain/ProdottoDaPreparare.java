package pizzeria.model.domain;

public class ProdottoDaPreparare {
    private final long numeroIstanza;
    private final String nomeProdotto;

    public ProdottoDaPreparare(long numeroIstanza, String nomeProdotto) {
        this.numeroIstanza = numeroIstanza;
        this.nomeProdotto = nomeProdotto;
    }

    public long getNumeroIstanza() {
        return numeroIstanza;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }
}
