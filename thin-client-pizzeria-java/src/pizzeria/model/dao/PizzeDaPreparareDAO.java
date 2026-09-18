package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.ProdottoDaPreparare;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzeDaPreparareDAO implements GenericProcedureDAO<List<ProdottoDaPreparare>> {

    @Override
    public List<ProdottoDaPreparare> execute(Object... params) throws DAOException {
        List<ProdottoDaPreparare> pizze = new ArrayList<>(); // lista di prodotti da preparare (pizze)
        // lista di oggetti quindi ogni elemento è un oggetto ProdottoDaPreparare

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.pizze_da_preparare()}")) {
                // La procedura restituisce il risultato di una SELECT.

                if (cs.execute()) {
                    // execute() restituisce true se il primo risultato è un ResultSet.
                    try (ResultSet rs = cs.getResultSet()) {
                        // Il risultato viene letto
                        while (rs.next()) {
                            /*
                                rs.next():
                                si sposta sulla riga successiva;
                                restituisce true se la riga esiste;
                                restituisce false quando le righe sono terminate.
                             */
                            pizze.add(new ProdottoDaPreparare(
                                    // per ogni riga legge le colonne
                                    rs.getLong("numero"),
                                    rs.getString("nome_prodotto")
                                    // I nomi devono corrispondere esattamente alle colonne restituite dalla procedura.
                            ));
                            // l'oggetto viene aggiunto alla lista dei prodotti da preparare (pizze)
                        }
                    }
                }
            }
            return pizze; // restituisce l'intera lista (se non ci sono pizze restituisce null)
        } catch (SQLException e) {
            throw new DAOException("Errore nella consultazione delle pizze: " + e.getMessage(), e);
        }
    }
}
