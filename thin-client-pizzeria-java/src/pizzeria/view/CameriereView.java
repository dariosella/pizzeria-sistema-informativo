package pizzeria.view;

import pizzeria.model.domain.Comanda;
import pizzeria.model.domain.ProdottoPronto;

import java.time.LocalDate;
import java.util.List;

public class CameriereView {

    public static int showMenu() {
        return ViewSupport.showMenu(
                "      CAMERIERE DASHBOARD      ",
                "Apri una comanda e associala a un tavolo",
                "Aggiungi un'ordinazione",
                "Consulta i prodotti pronti e il relativo tavolo",
                "Esci"
        );
    }

    // Servono a evitare che il controller dipenda direttamente dalla classe di supporto.
    public static int readUnsignedInt(String label) {
        return ViewSupport.readUnsignedInt(label);
    }

    public static LocalDate readDate(String label) {
        return ViewSupport.readDate(label);
    }

    public static String readText(String label) {
        return ViewSupport.readText(label);
    }
    //

    public static void showComanda(Comanda comanda) {
        ViewSupport.showSuccess("comanda aperta e associata al tavolo");
        System.out.println("Data comanda: " + comanda.getData());
        System.out.println("Numero comanda: " + comanda.getNumero());
    }

    public static void showProdottiPronti(List<ProdottoPronto> prodotti) {
        if (prodotti.isEmpty()) {
            ViewSupport.showInfo("Nessun prodotto pronto da servire.");
            return;
        }

        System.out.printf("%-18s | %-32s | %s%n",
                "Numero istanza", "Prodotto", "Tavolo");
        for (ProdottoPronto prodotto : prodotti) {
            System.out.printf("%-18d | %-32s | %d%n",
                    prodotto.getNumeroIstanza(),
                    prodotto.getNomeProdotto(),
                    prodotto.getNumeroTavolo());
        }
    }

    public static void showSuccess(String message) {
        ViewSupport.showSuccess(message);
    }

    public static void showError(String message) {
        ViewSupport.showError(message);
    }
}
