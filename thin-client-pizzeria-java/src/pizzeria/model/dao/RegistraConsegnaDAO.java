package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.Comanda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class RegistraConsegnaDAO implements GenericProcedureDAO<Comanda> {

    @Override
    public Comanda execute(Object... params) throws DAOException {
        String nomeCliente = (String) params[0];
        String indirizzo = (String) params[1];
        String recapitoTelefonico = (String) params[2];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall(
                    "{call Pizzeria.registra_consegna(?,?,?,?,?)}")) {
                cs.setString(1, nomeCliente); // IN
                cs.setString(2, indirizzo); // IN
                cs.setString(3, recapitoTelefonico); // IN
                cs.registerOutParameter(4, Types.DATE);
                cs.registerOutParameter(5, Types.SMALLINT);
                cs.execute();

                return new Comanda(
                        cs.getDate(4).toLocalDate(),
                        cs.getInt(5)
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella registrazione dell'ordine in consegna: "
                    + e.getMessage(), e);
        }
    }
}
