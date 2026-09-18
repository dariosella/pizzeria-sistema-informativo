package pizzeria.view;

import pizzeria.model.domain.Credentials;

// si limita a raccogliere dati
public class LoginView {
    public static Credentials authenticate() {
        System.out.println("\n******** LOGIN PIZZERIA ********");
        String username = ViewSupport.readText("Username");
        String password = ViewSupport.readText("Password");

        return new Credentials(username, password, null);
    }

    public static void showError(String message) {
        ViewSupport.showError(message);
    }
}
