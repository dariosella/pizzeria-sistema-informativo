package pizzeria.controller;

import pizzeria.model.dao.ConnectionFactory;
import pizzeria.model.domain.Credentials;
import pizzeria.view.LoginView;

// È il controller principale.
public class ApplicationController implements Controller {
    /*
    Contiene le credenziali dell’utente autenticato.
    */
    private Credentials cred = null;

    @Override
    public void start() {
        /*
        Il login viene ripetuto finché:
        non esistono credenziali valide;
        oppure non è stato riconosciuto un ruolo.
         */
        while (cred == null || cred.getRole() == null) {
            /*
            viene creato il controller del login;
            viene avviato;
            vengono recuperate le credenziali risultanti.
             */
            LoginController loginController = new LoginController();
            loginController.start();
            /*
            Restituisce un oggetto Credentials
            Contiene le credenziali di un'account tecnico che si trova in db.properties
            username = {db_manager, db_cameriere, db_pizzaiolo, db_barman}
            password = {..., ..., ..., ...}
            role = {MANAGER, CAMERIERE, PIZZAIOLO, BARMAN}
            */
            cred = loginController.getCred();

            // se le credenziali non sono corrette
            if (cred == null || cred.getRole() == null) {
                LoginView.showError("Username o password non corretti");
            }
        }

        // se le credenziali sono corrette
        try {
            // Il ruolo determina quale menu viene avviato.
            switch (cred.getRole()) {
                case MANAGER -> new ManagerController().start();
                case CAMERIERE -> new CameriereController().start();
                case PIZZAIOLO -> new PizzaioloController().start();
                case BARMAN -> new BarmanController().start();
            }
        } finally {
            /*
            Il blocco finally viene eseguito comunque, sia in caso di uscita normale sia in caso di errore.
            Quando il controller del ruolo termina, la connessione MySQL viene chiusa.
             */
            ConnectionFactory.close();
        }
    }
}
