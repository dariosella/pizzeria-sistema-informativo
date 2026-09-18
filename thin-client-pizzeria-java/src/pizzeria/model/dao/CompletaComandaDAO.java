package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class CompletaComandaDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        LocalDate dataComanda = (LocalDate) params[0];
        int numeroComanda = (Integer) params[1];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall(
                    "{call Pizzeria.completa_comanda(?,?)}")) {
                cs.setDate(1, Date.valueOf(dataComanda));
                cs.setInt(2, numeroComanda);
                cs.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nel completamento della comanda: " + e.getMessage(), e);
        }
    }
}
