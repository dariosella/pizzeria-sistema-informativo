package pizzeria.controller;

import pizzeria.exception.DAOException;
import pizzeria.model.dao.AggiungiOrdinazioneDAO;
import pizzeria.model.dao.ApriComandaTavoloDAO;
import pizzeria.model.dao.ConnectionFactory;
import pizzeria.model.dao.ProdottiProntiTavoliDAO;
import pizzeria.model.domain.Comanda;
import pizzeria.model.domain.ProdottoPronto;
import pizzeria.model.domain.Role;
import pizzeria.view.CameriereView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CameriereController implements Controller {

    @Override
    public void start() {
        try {
            /*
            La connessione utilizzata per il login viene chiusa
            viene aperta una nuova connessione usando l’account tecnico db_cameriere.
            */
            ConnectionFactory.changeRole(Role.CAMERIERE);
        } catch (SQLException e) {
            // Se la connessione fallisce il controller termina
            CameriereView.showError("Connessione come cameriere non riuscita: " + e.getMessage());
            return;
        }

        while (true) {
            int choice = CameriereView.showMenu();

            try {
                switch (choice) {
                    case 1 -> apriComandaTavolo();
                    case 2 -> aggiungiOrdinazione();
                    case 3 -> prodottiProntiTavoli();
                    case 4 -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Scelta non valida");
                }
            } catch (DAOException e) {
                CameriereView.showError(e.getMessage());
            }
        }
    }

    private void apriComandaTavolo() throws DAOException {
        // La view legge il tavolo.
        int numeroTavolo = CameriereView.readUnsignedInt("Numero del tavolo");
        // Il DAO chiama la procedura SQL apri_comanda_tavolo e restituisce data e numero della nuova comanda.
        Comanda comanda = new ApriComandaTavoloDAO().execute(numeroTavolo);
        // La view mostra il risultato.
        CameriereView.showComanda(comanda);
    }

    private void aggiungiOrdinazione() throws DAOException {
        // La view legge la data
        LocalDate data = CameriereView.readDate("Data della comanda");
        // La view legge il numero della comanda
        int numero = CameriereView.readUnsignedInt("Numero della comanda");
        // La view legge il nome del prodotto
        String prodotto = CameriereView.readText("Nome del prodotto");
        // Il DAO chiama la procedura SQL aggiungi_ordinazione
        new AggiungiOrdinazioneDAO().execute(data, numero, prodotto);
        CameriereView.showSuccess("ordinazione aggiunta");
    }

    private void prodottiProntiTavoli() throws DAOException {
        // Il DAO restituisce una lista di prodotti pronti con il relativo tavolo
        List<ProdottoPronto> prodotti = new ProdottiProntiTavoliDAO().execute();
        // La lista viene passata alla view
        CameriereView.showProdottiPronti(prodotti);
    }
}
