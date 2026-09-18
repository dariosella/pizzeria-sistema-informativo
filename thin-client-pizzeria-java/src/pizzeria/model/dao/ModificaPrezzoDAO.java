package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ModificaPrezzoDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        String nomeProdotto = (String) params[0];
        BigDecimal nuovoPrezzo = (BigDecimal) params[1];

        try {
            Connection conn = ConnectionFactory.getConnection();
            // CallableStatement è l’oggetto JDBC usato per chiamare stored procedure.
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.modifica_prezzo(?,?)}")) {
                // chiama una procedura, nello schema Pizzeria, chiamata modifica_prezzo, con due parametri.
                // i punti interrogativi sono segnaposto
                cs.setString(1, nomeProdotto); // assegna il primo parametro
                cs.setBigDecimal(2, nuovoPrezzo); // assegna il secondo parametro
                cs.execute(); // invia la chiamata a MySQL
                return null;
            }
            /*
                try chiude automaticamente il CallableStatement, anche se si verifica un errore.
                la connessione non viene chiusa perché è gestita centralmente da ConnectionFactory.
            */
        } catch (SQLException e) {
            throw new DAOException("Errore nella modifica del prezzo: " + e.getMessage(), e);
        }
    }
}
