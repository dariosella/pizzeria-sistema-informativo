package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AggiungiOrdinazioneDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        LocalDate dataComanda = (LocalDate) params[0];
        int numeroComanda = (Integer) params[1];
        String nomeProdotto = (String) params[2];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall(
                    "{call Pizzeria.aggiungi_ordinazione(?,?,?)}")) {
                cs.setDate(1, Date.valueOf(dataComanda)); // IN
                cs.setInt(2, numeroComanda); // IN
                cs.setString(3, nomeProdotto); // IN
                cs.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nell'aggiunta dell'ordinazione: " + e.getMessage(), e);
        }
    }
}
