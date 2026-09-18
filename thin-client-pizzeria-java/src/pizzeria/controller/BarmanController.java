package pizzeria.controller;

import pizzeria.exception.DAOException;
import pizzeria.model.dao.BevandeDaPreparareDAO;
import pizzeria.model.dao.ConnectionFactory;
import pizzeria.model.dao.SegnaBevandaProntaDAO;
import pizzeria.model.domain.ProdottoDaPreparare;
import pizzeria.model.domain.Role;
import pizzeria.view.BarmanView;

import java.sql.SQLException;
import java.util.List;

public class BarmanController implements Controller {

    @Override
    public void start() {
        try {
            // Apre una connessione come db_barman
            ConnectionFactory.changeRole(Role.BARMAN);
        } catch (SQLException e) {
            BarmanView.showError("Connessione come barman non riuscita: " + e.getMessage());
            return;
        }

        while (true) {
            int choice = BarmanView.showMenu();

            try {
                switch (choice) {
                    case 1 -> bevandeDaPreparare();
                    case 2 -> segnaBevandaPronta();
                    case 3 -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Scelta non valida");
                }
            } catch (DAOException e) {
                BarmanView.showError(e.getMessage());
            }
        }
    }

    private void bevandeDaPreparare() throws DAOException {
        List<ProdottoDaPreparare> bevande = new BevandeDaPreparareDAO().execute();
        BarmanView.showBevande(bevande);
    }

    private void segnaBevandaPronta() throws DAOException {
        long numeroIstanza = BarmanView.readNumeroIstanza();
        new SegnaBevandaProntaDAO().execute(numeroIstanza);
        BarmanView.showSuccess("procedura di aggiornamento della bevanda eseguita");
    }
}
