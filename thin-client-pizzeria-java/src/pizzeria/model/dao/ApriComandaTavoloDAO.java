package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.Comanda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ApriComandaTavoloDAO implements GenericProcedureDAO<Comanda> {

    @Override
    public Comanda execute(Object... params) throws DAOException {
        int numeroTavolo = (Integer) params[0]; // numero del tavolo

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.apri_comanda_tavolo(?,?,?)}")) {
                cs.setInt(1, numeroTavolo); // numero tavolo IN
                cs.registerOutParameter(2, Types.DATE); // data nuova comanda OUT
                cs.registerOutParameter(3, Types.SMALLINT); // numero nuova comanda OUT
                cs.execute();

                // questo oggetto torna al controller, che lo passa alla view per mostrarlo
                return new Comanda(
                        // legge i valori prodotti dalla procedura
                        cs.getDate(2).toLocalDate(),
                        cs.getInt(3)
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nell'apertura della comanda: " + e.getMessage(), e);
        }
    }
}
