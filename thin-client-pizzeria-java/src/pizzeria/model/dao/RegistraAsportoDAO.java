package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.Comanda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class RegistraAsportoDAO implements GenericProcedureDAO<Comanda> {

    @Override
    public Comanda execute(Object... params) throws DAOException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.registra_asporto(?,?)}")) {
                cs.registerOutParameter(1, Types.DATE); // OUT
                cs.registerOutParameter(2, Types.SMALLINT); // OUT
                cs.execute();

                return new Comanda(
                        cs.getDate(1).toLocalDate(),
                        cs.getInt(2)
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella registrazione dell'ordine da asporto: "
                    + e.getMessage(), e);
        }
    }
}
