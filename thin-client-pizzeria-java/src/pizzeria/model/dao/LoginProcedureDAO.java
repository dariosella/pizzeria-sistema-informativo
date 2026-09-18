package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.Credentials;
import pizzeria.model.domain.Role;

import java.sql.*;

// restituisce un oggetto Credentials.
public class LoginProcedureDAO implements GenericProcedureDAO<Credentials> {

    @Override
    public Credentials execute(Object... params) throws DAOException {
        // Il cast converte gli elementi dell’array Object[] nel tipo corretto.
        String username = (String) params[0]; // il primo parametro è lo username
        String password = (String) params[1]; // il secondo parametro è la password
        int role; // variabile per memorizzare il ruolo che verrà restituito dalla procedura di login

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.login(?,?,?)}")) {
                cs.setString(1, username); // IN
                cs.setString(2, password); // IN
                cs.registerOutParameter(3, Types.INTEGER); // OUT
                cs.execute();
                role = cs.getInt(3);
            }
        } catch(SQLException e) {
            throw new DAOException("Errore durante il login: " + e.getMessage(), e);
        }

        // Se il database restituisce un valore non riconosciuto
        // Role.fromInt() restituisce null e ApplicationController ripete il login.
        return new Credentials(username, password, Role.fromInt(role));
        // il DAO trasforma il risultato del database in un oggetto Credentials
        // tale oggetto viene restituito allo start() di LoginController
        // lo start() di LoginController restituisce l'oggetto allo start() di ApplicationController
    }
}
