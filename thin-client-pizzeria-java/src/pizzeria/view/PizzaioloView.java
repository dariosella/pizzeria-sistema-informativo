package pizzeria.view;

import pizzeria.model.domain.ProdottoDaPreparare;

import java.util.List;

public class PizzaioloView {

    public static int showMenu() {
        return ViewSupport.showMenu(
                "      PIZZAIOLO DASHBOARD      ",
                "Consulta le pizze da preparare",
                "Segnala una pizza come pronta",
                "Esci"
        );
    }

    public static long readNumeroIstanza() {
        return ViewSupport.readUnsignedLong("Numero dell'istanza");
    }

    public static void showPizze(List<ProdottoDaPreparare> prodotti) {
        if (prodotti.isEmpty()) {
            ViewSupport.showInfo("Nessuna pizza da preparare");
            return;
        }

        System.out.printf("%-18s | %s%n", "Numero istanza", "Prodotto");
        for (ProdottoDaPreparare prodotto : prodotti) {
            System.out.printf("%-18d | %s%n",
                    prodotto.getNumeroIstanza(),
                    prodotto.getNomeProdotto());
        }
    }

    public static void showSuccess(String message) {
        ViewSupport.showSuccess(message);
    }

    public static void showError(String message) {
        ViewSupport.showError(message);
    }
}
