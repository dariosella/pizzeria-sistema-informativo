package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.RigaScontrino;
import pizzeria.model.domain.Scontrino;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneraScontrinoDAO implements GenericProcedureDAO<Scontrino> {

    @Override
    public Scontrino execute(Object... params) throws DAOException {
        /*
            La procedura genera_scontrino esegue due SELECT:
            righe dello scontrino;
            totale della comanda.
            Perciò restituisce due ResultSet.
         */
        LocalDate dataComanda = (LocalDate) params[0];
        int numeroComanda = (Integer) params[1];

        // i risultati della stored procedure: lista di righe + il totale complessivo
        List<RigaScontrino> righe = new ArrayList<>();
        BigDecimal totale = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.genera_scontrino(?,?)}")) {
                cs.setDate(1, Date.valueOf(dataComanda));
                cs.setInt(2, numeroComanda);

                boolean result = cs.execute(); // esegue la genera_scontrino
                /*
                result vale:
                true se il risultato corrente è un ResultSet;
                false se è un contatore di aggiornamento o non c’è un risultato.
                */
                while (!result && cs.getUpdateCount() != -1) {
                    result = cs.getMoreResults();
                }

                if (result) {
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            // Le righe vengono convertite in oggetti RigaScontrino
                            righe.add(new RigaScontrino(
                                    rs.getString("nome_prodotto"),
                                    rs.getInt("quantita"),
                                    rs.getBigDecimal("prezzo_unitario"),
                                    rs.getBigDecimal("subtotale")
                            ));
                        }
                    }
                }

                result = cs.getMoreResults();
                while (!result && cs.getUpdateCount() != -1) {
                    result = cs.getMoreResults();
                }

                if (result) {
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs.next()) {
                            totale = rs.getBigDecimal("totale");
                        }
                    }
                }
            }

            return new Scontrino(righe, totale);
        } catch (SQLException e) {
            throw new DAOException("Errore nella generazione dello scontrino: "
                    + e.getMessage(), e);
        }
    }
}
