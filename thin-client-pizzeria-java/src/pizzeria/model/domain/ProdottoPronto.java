package pizzeria.model.domain;

public class ProdottoPronto {
    private final long numeroIstanza;
    private final String nomeProdotto;
    private final int numeroTavolo;

    public ProdottoPronto(long numeroIstanza, String nomeProdotto, int numeroTavolo) {
        this.numeroIstanza = numeroIstanza;
        this.nomeProdotto = nomeProdotto;
        this.numeroTavolo = numeroTavolo;
    }

    public long getNumeroIstanza() {
        return numeroIstanza;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public int getNumeroTavolo() {
        return numeroTavolo;
    }
}
