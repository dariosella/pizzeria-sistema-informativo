package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

// La procedura non restituisce dati quindi il tipo restituito è Void
public class CreaUserDAO implements GenericProcedureDAO<Void> {

    @Override
    public Void execute(Object... params) throws DAOException {
        String username = (String) params[0];
        String password = (String) params[1];
        Role role = (Role) params[2];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.crea_user(?,?,?)}")) {
                cs.setString(1, username);
                cs.setString(2, password);
                cs.setString(3, role.getDatabaseValue()); // ad ex. MANAGER -> 'manager'
                cs.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella creazione dell'utente: " + e.getMessage(), e);
        }
    }
}
