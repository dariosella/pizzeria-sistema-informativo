package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class LiberaTavoloDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        int numeroTavolo = (Integer) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.libera_tavolo(?)}")) {
                cs.setInt(1, numeroTavolo);
                cs.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella liberazione del tavolo: " + e.getMessage(), e);
        }
    }
}
