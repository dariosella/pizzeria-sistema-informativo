package pizzeria.controller;

import pizzeria.exception.DAOException;
import pizzeria.model.dao.LoginProcedureDAO;
import pizzeria.model.domain.Credentials;
import pizzeria.view.LoginView;

// coordina LoginView e LoginProcedureDAO
public class LoginController implements Controller {
    Credentials cred = null; // Memorizza il risultato del login effettuato da LoginProcedureDAO

    @Override
    public void start() {
        /*
        La view chiede username e password
        restituisce un oggetto Credentials ancora privo di ruolo.
        */
        cred = LoginView.authenticate();

        try {
            /*
            creato un LoginProcedureDAO;
            chiamato il metodo execute;
            passato username;
            passata password;
            ricevuto un nuovo oggetto Credentials, questa volta contenente anche il ruolo.
            Le nuove credenziali sono quelle presenti in db.properties, fanno riferimento a un'account tecnico
            username = {db_manager, db_cameriere, db_pizzaiolo, db_barman}
            password = {..., ..., ..., ...}
            role = {MANAGER, CAMERIERE, PIZZAIOLO, BARMAN}
            */
            cred = new LoginProcedureDAO().execute(cred.getUsername(), cred.getPassword());
        } catch(DAOException e) {
            // Se la chiamata MySQL fallisce, viene mostrato l’errore e le credenziali vengono annullate.
            LoginView.showError(e.getMessage());
            cred = null;
        }
    }

    // Permette ad ApplicationController di recuperare il risultato del login.
    public Credentials getCred() {
        return cred;
    }
}
