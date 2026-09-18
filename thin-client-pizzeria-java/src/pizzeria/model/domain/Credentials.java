package pizzeria.model.domain;

// Rappresenta l’identità dell’utente applicativo.
public class Credentials {
    // gli attributi sono final quindi l'oggetto è immutabile dopo la creazione
    private final String username;
    private final String password;
    private final Role role;

    public Credentials(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // I getters permettono di leggere i valori
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}