package pizzeria.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/*
la classe di supporto comune a tutte le view.
Non è public, quindi è accessibile solamente dal package pizzeria.view.
final impedisce di estenderla.
*/
final class ViewSupport {
    // Tutte le view usano lo stesso Scanner.
    private static final Scanner INPUT = new Scanner(System.in);

    // Costruttore privato Impedisce la creazione di oggetti ViewSupport
    // perché tutti i metodi sono statici.
    private ViewSupport() {
    }

    static int showMenu(String title, String... options) {
        /*
            stampa le opzioni numerate;
            legge una riga;
            prova a convertirla in intero;
            controlla che il valore sia compreso nell’intervallo;
            ripete la richiesta se il valore non è valido.
         */
        while (true) {
            System.out.println("\n*********************************");
            System.out.println(title);
            System.out.println("*********************************");

            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ") " + options[i]);
            }

            System.out.print("Scelta: ");
            String value = INPUT.nextLine().trim(); // trim() elimina spazi iniziali e finali

            try {
                int choice = Integer.parseInt(value); // prova a trasformarla in intero
                // se l'input non è numerico viene generata una NumberFormatException
                if (choice >= 1 && choice <= options.length) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {
                // Il messaggio viene mostrato sotto.
            }

            showError("Scelta non valida");
        }
    }

    static String readText(String label) {
        while (true) {
            System.out.print(label + ": ");
            String value = INPUT.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            showError("Il valore non può essere vuoto");
        }
    }

    static int readUnsignedInt(String label) {
        /*
        Controlla che l’input:
        sia un intero;
        sia maggiore o uguale a zero.
        */
        while (true) {
            String value = readText(label);
            try {
                int number = Integer.parseInt(value);
                if (number >= 0) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // Il messaggio viene mostrato sotto.
            }
            showError("Inserire un numero intero non negativo");
        }
    }

    static long readUnsignedLong(String label) {
        /*
        Controlla che l’input:
        sia un long;
        sia maggiore o uguale a zero.
        */
        while (true) {
            String value = readText(label);
            try {
                long number = Long.parseLong(value);
                if (number >= 0) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // Il messaggio viene mostrato sotto.
            }
            showError("Inserire un numero intero non negativo");
        }
    }

    static BigDecimal readDecimal(String label) {
        while (true) {
            String value = readText(label).replace(',', '.');
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                showError("Inserire un prezzo valido");
            }
        }
    }

    static LocalDate readDate(String label) {
        // AAAA-MM-GG
        while (true) {
            String value = readText(label + " (AAAA-MM-GG)");
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                showError("Inserire una data nel formato AAAA-MM-GG");
            }
        }
    }

    static YearMonth readYearMonth(String label) {
        // AAAA-MM
        while (true) {
            String value = readText(label + " (AAAA-MM)");
            try {
                return YearMonth.parse(value);
            } catch (DateTimeParseException e) {
                showError("Inserire un periodo nel formato AAAA-MM");
            }
        }
    }

    static void showSuccess(String message) {
        System.out.println("\nOperazione eseguita: " + message);
    }

    static void showError(String message) {
        System.out.println("\nErrore: " + message);
    }

    static void showInfo(String message) {
        System.out.println("\n" + message);
    }

    static String money(BigDecimal value) {
        return value == null ? "-" : value.setScale(2).toPlainString();
    }
}
