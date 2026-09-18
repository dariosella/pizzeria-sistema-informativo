package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SegnaBevandaProntaDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        long numeroIstanza = (Long) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.segna_bevanda_pronta(?)}")) {
                cs.setLong(1, numeroIstanza);
                cs.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nell'aggiornamento della bevanda: " + e.getMessage(), e);
        }
    }
}
