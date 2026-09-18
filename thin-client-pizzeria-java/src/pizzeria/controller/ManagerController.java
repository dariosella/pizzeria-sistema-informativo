package pizzeria.controller;

import pizzeria.exception.DAOException;
import pizzeria.model.dao.AggiungiOrdinazioneDAO;
import pizzeria.model.dao.CompletaComandaDAO;
import pizzeria.model.dao.ConnectionFactory;
import pizzeria.model.dao.CreaUserDAO;
import pizzeria.model.dao.EntrataGiornalieraDAO;
import pizzeria.model.dao.EntrataMensileDAO;
import pizzeria.model.dao.GeneraScontrinoDAO;
import pizzeria.model.dao.LiberaTavoloDAO;
import pizzeria.model.dao.ModificaPrezzoDAO;
import pizzeria.model.dao.RegistraAsportoDAO;
import pizzeria.model.dao.RegistraConsegnaDAO;
import pizzeria.model.domain.Comanda;
import pizzeria.model.domain.EntrataGiornaliera;
import pizzeria.model.domain.EntrataMensile;
import pizzeria.model.domain.Role;
import pizzeria.model.domain.Scontrino;
import pizzeria.view.ManagerView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

public class ManagerController implements Controller {

    @Override
    public void start() {
        try {
            // Apre una connessione come db_manager.
            ConnectionFactory.changeRole(Role.MANAGER);
        } catch (SQLException e) {
            ManagerView.showError("Connessione come manager non riuscita: " + e.getMessage());
            return;
        }

        while (true) {
            int choice = ManagerView.showMenu();

            try {
                switch (choice) {
                    case 1 -> creaUser();
                    case 2 -> aggiungiOrdinazione();
                    case 3 -> completaComanda();
                    case 4 -> generaScontrino();
                    case 5 -> entrataGiornaliera();
                    case 6 -> entrataMensile();
                    case 7 -> liberaTavolo();
                    case 8 -> registraAsporto();
                    case 9 -> registraConsegna();
                    case 10 -> modificaPrezzo();
                    case 11 -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Scelta non valida");
                }
            } catch (DAOException e) {
                ManagerView.showError(e.getMessage());
            }
        }
    }

    private void creaUser() throws DAOException {
        String username = ManagerView.readText("Username del nuovo utente");
        String password = ManagerView.readText("Password del nuovo utente");
        Role role = ManagerView.readRole();

        new CreaUserDAO().execute(username, password, role);
        ManagerView.showSuccess("utente creato con ruolo " + role.getDatabaseValue());
    }

    private void aggiungiOrdinazione() throws DAOException {
        LocalDate data = ManagerView.readDate("Data della comanda");
        int numero = ManagerView.readUnsignedInt("Numero della comanda");
        String prodotto = ManagerView.readText("Nome del prodotto");

        new AggiungiOrdinazioneDAO().execute(data, numero, prodotto);
        ManagerView.showSuccess("ordinazione aggiunta");
    }

    private void completaComanda() throws DAOException {
        LocalDate data = ManagerView.readDate("Data della comanda");
        int numero = ManagerView.readUnsignedInt("Numero della comanda");
        // Il controllo dei prodotti pronti e il calcolo del conto avvengono nel database.

        new CompletaComandaDAO().execute(data, numero);
        ManagerView.showSuccess("procedura di completamento eseguita");
    }

    private void generaScontrino() throws DAOException {
        // Legge data e numero
        LocalDate data = ManagerView.readDate("Data della comanda");
        int numero = ManagerView.readUnsignedInt("Numero della comanda");
        /*
        Il risultato contiene:
        righe raggruppate per prodotto e prezzo;
        quantità;
        subtotali;
        totale complessivo.
        */
        Scontrino scontrino = new GeneraScontrinoDAO().execute(data, numero);
        ManagerView.showScontrino(scontrino);
    }

    private void entrataGiornaliera() throws DAOException {
        LocalDate data = ManagerView.readDate("Giorno");
        EntrataGiornaliera entrata = new EntrataGiornalieraDAO().execute(data);
        // Se la vista non restituisce righe, il DAO restituisce null.
        ManagerView.showEntrataGiornaliera(entrata);
    }

    private void entrataMensile() throws DAOException {
        // YearMonth rappresenta solamente anno e mese
        YearMonth periodo = ManagerView.readYearMonth("Mese");
        EntrataMensile entrata = new EntrataMensileDAO().execute(periodo);
        ManagerView.showEntrataMensile(entrata);
    }

    private void liberaTavolo() throws DAOException {
        // Legge il numero del tavolo
        int numeroTavolo = ManagerView.readUnsignedInt("Numero del tavolo");
        new LiberaTavoloDAO().execute(numeroTavolo);
        ManagerView.showSuccess("procedura di liberazione del tavolo eseguita");
    }

    private void registraAsporto() throws DAOException {
        // La procedura decide automaticamente data e numero della nuova comanda.
        Comanda comanda = new RegistraAsportoDAO().execute();
        // La procedura restituisce data e numero della comanda creata.
        ManagerView.showComanda("ordine da asporto registrato", comanda);
    }

    private void registraConsegna() throws DAOException {
        /*
        Legge:
        nome cliente;
        indirizzo;
        recapito telefonico.
        La procedura decide automaticamente data e numero della nuova comanda.
         */
        String nomeCliente = ManagerView.readText("Nome del cliente");
        String indirizzo = ManagerView.readText("Indirizzo");
        String telefono = ManagerView.readText("Recapito telefonico");

        Comanda comanda = new RegistraConsegnaDAO().execute(nomeCliente, indirizzo, telefono);
        // La procedura restituisce data e numero della comanda creata.
        ManagerView.showComanda("ordine in consegna registrato", comanda);
    }

    private void modificaPrezzo() throws DAOException {
        String prodotto = ManagerView.readText("Nome del prodotto");
        // BigDecimal viene utilizzato perché è adatto ai valori monetari.
        BigDecimal prezzo = ManagerView.readDecimal("Nuovo prezzo");

        new ModificaPrezzoDAO().execute(prodotto, prezzo);
        ManagerView.showSuccess("procedura di modifica del prezzo eseguita");
    }

}
