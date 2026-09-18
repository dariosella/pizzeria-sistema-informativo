package pizzeria.controller;

import pizzeria.exception.DAOException;
import pizzeria.model.dao.ConnectionFactory;
import pizzeria.model.dao.PizzeDaPreparareDAO;
import pizzeria.model.dao.SegnaPizzaProntaDAO;
import pizzeria.model.domain.ProdottoDaPreparare;
import pizzeria.model.domain.Role;
import pizzeria.view.PizzaioloView;

import java.sql.SQLException;
import java.util.List;

public class PizzaioloController implements Controller {

    @Override
    public void start() {
        try {
            // Apre una connessione come db_pizzaiolo.
            ConnectionFactory.changeRole(Role.PIZZAIOLO);
        } catch (SQLException e) {
            PizzaioloView.showError("Connessione come pizzaiolo non riuscita: " + e.getMessage());
            return;
        }

        while (true) {
            int choice = PizzaioloView.showMenu();

            try {
                switch (choice) {
                    case 1 -> pizzeDaPreparare();
                    case 2 -> segnaPizzaPronta();
                    case 3 -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Scelta non valida");
                }
            } catch (DAOException e) {
                PizzaioloView.showError(e.getMessage());
            }
        }
    }

    private void pizzeDaPreparare() throws DAOException {
        List<ProdottoDaPreparare> pizze = new PizzeDaPreparareDAO().execute();
        PizzaioloView.showPizze(pizze);
    }

    private void segnaPizzaPronta() throws DAOException {
        // Viene letto l’identificatore dell’istanza.
        long numeroIstanza = PizzaioloView.readNumeroIstanza();
        // Il DAO chiama la relativa stored procedure.
        new SegnaPizzaProntaDAO().execute(numeroIstanza);
        PizzaioloView.showSuccess("procedura di aggiornamento della pizza eseguita");
    }
}
