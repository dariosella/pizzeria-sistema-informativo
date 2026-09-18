package pizzeria.view;

import pizzeria.model.domain.ProdottoDaPreparare;

import java.util.List;

public class BarmanView {

    public static int showMenu() {
        return ViewSupport.showMenu(
                "        BARMAN DASHBOARD       ",
                "Consulta le bevande da preparare",
                "Segnala una bevanda come pronta",
                "Esci"
        );
    }

    public static long readNumeroIstanza() {
        return ViewSupport.readUnsignedLong("Numero dell'istanza");
    }

    public static void showBevande(List<ProdottoDaPreparare> bevande) {
        if (bevande.isEmpty()) {
            ViewSupport.showInfo("Nessuna bevanda da preparare.");
            return;
        }

        System.out.printf("%-18s | %s%n", "Numero istanza", "Prodotto");
        for (ProdottoDaPreparare bevanda : bevande) {
            System.out.printf("%-18d | %s%n",
                    bevanda.getNumeroIstanza(),
                    bevanda.getNomeProdotto());
        }
    }

    public static void showSuccess(String message) {
        ViewSupport.showSuccess(message);
    }

    public static void showError(String message) {
        ViewSupport.showError(message);
    }
}
