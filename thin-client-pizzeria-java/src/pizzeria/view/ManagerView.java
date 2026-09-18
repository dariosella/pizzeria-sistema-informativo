package pizzeria.view;

import pizzeria.model.domain.Comanda;
import pizzeria.model.domain.EntrataGiornaliera;
import pizzeria.model.domain.EntrataMensile;
import pizzeria.model.domain.RigaScontrino;
import pizzeria.model.domain.Role;
import pizzeria.model.domain.Scontrino;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public class ManagerView {

    public static int showMenu() {
        return ViewSupport.showMenu(
                "       MANAGER DASHBOARD       ",
                "Crea un nuovo utente",
                "Aggiungi un'ordinazione",
                "Completa una comanda",
                "Genera lo scontrino",
                "Visualizza un'entrata giornaliera",
                "Visualizza un'entrata mensile",
                "Libera un tavolo",
                "Registra un ordine da asporto",
                "Registra un ordine in consegna",
                "Modifica il prezzo di un prodotto",
                "Esci"
        );
    }

    public static String readText(String label) {
        return ViewSupport.readText(label);
    }

    public static int readUnsignedInt(String label) {
        return ViewSupport.readUnsignedInt(label);
    }

    public static LocalDate readDate(String label) {
        return ViewSupport.readDate(label);
    }

    public static YearMonth readYearMonth(String label) {
        return ViewSupport.readYearMonth(label);
    }

    public static BigDecimal readDecimal(String label) {
        return ViewSupport.readDecimal(label);
    }

    public static Role readRole() {
        int choice = ViewSupport.showMenu(
                "     RUOLO DEL NUOVO UTENTE    ",
                "Manager",
                "Cameriere",
                "Pizzaiolo",
                "Barman"
        );

        return switch (choice) {
            case 1 -> Role.MANAGER;
            case 2 -> Role.CAMERIERE;
            case 3 -> Role.PIZZAIOLO;
            case 4 -> Role.BARMAN;
            default -> throw new IllegalStateException("Ruolo non valido");
        };
    }

    public static void showComanda(String message, Comanda comanda) {
        // showComanda() riceve anche un messaggio
        // così può essere riutilizzato sia per l’asporto sia per la consegna.
        ViewSupport.showSuccess(message);
        System.out.println("Data comanda: " + comanda.getData());
        System.out.println("Numero comanda: " + comanda.getNumero());
    }

    public static void showScontrino(Scontrino scontrino) {
        if (scontrino.getRighe().isEmpty() || scontrino.getTotale() == null) {
            ViewSupport.showInfo("Nessuno scontrino disponibile per la comanda indicata.");
            return;
        }

        System.out.println("\n******** SCONTRINO NON FISCALE ********");
        System.out.printf("%-32s | %8s | %12s | %12s%n",
                "Prodotto", "Quantità", "Prezzo", "Subtotale");

        for (RigaScontrino riga : scontrino.getRighe()) {
            System.out.printf("%-32s | %8d | %10s € | %10s €%n",
                    riga.getNomeProdotto(),
                    riga.getQuantita(),
                    ViewSupport.money(riga.getPrezzoUnitario()),
                    ViewSupport.money(riga.getSubtotale()));
        }

        System.out.println("Totale: " + ViewSupport.money(scontrino.getTotale()) + " €");
    }

    public static void showEntrataGiornaliera(EntrataGiornaliera entrata) {
        if (entrata == null) {
            ViewSupport.showInfo("Nessuna entrata registrata per il giorno indicato.");
        } else {
            System.out.println("Entrata del " + entrata.getData() + ": "
                    + ViewSupport.money(entrata.getTotale()) + " €");
        }
    }

    public static void showEntrataMensile(EntrataMensile entrata) {
        if (entrata == null) {
            ViewSupport.showInfo("Nessuna entrata registrata per il mese indicato.");
        } else {
            System.out.printf("Entrata di %04d-%02d: %s €%n",
                    entrata.getAnno(),
                    entrata.getMese(),
                    ViewSupport.money(entrata.getTotale()));
        }
    }

    public static void showSuccess(String message) {
        ViewSupport.showSuccess(message);
    }

    public static void showError(String message) {
        ViewSupport.showError(message);
    }
}
