package pizzeria.model.dao;

import pizzeria.model.domain.Role;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Centralizza l'apertura e la chiusura della connessione al database
public final class ConnectionFactory {
    // Esiste una sola connessione corrente per tutta l’applicazione.
    private static Connection connection; // connessione condivisa

    // All'avvio viene usato l'account tecnico db_login che può eseguire soltanto il login.
    // Prima dell’autenticazione viene usato l’account tecnico db_login.
    private static String currentRole = "LOGIN";

    // Il costruttore privato impedisce di creare oggetti di tipo ConnectionFactory
    // la classe viene utilizzata solamente attraverso metodi e attributi statici
    private ConnectionFactory() {}

    // Un metodo synchronized permette a un solo thread alla volta di modificare la connessione condivisa.
    public static synchronized Connection getConnection() throws SQLException {
        // se non esiste una connessione, ne apre una
        // se esiste ed è ancora aperta, la riutilizza
        // La connessione viene aperta solamente quando serve e poi riutilizzata.
        if (connection == null || connection.isClosed()) {
            connection = openConnection(currentRole);
        }
        return connection; // ritorna l'oggetto di connessione
    }

    // Un metodo synchronized permette a un solo thread alla volta di modificare la connessione condivisa.
    public static synchronized void changeRole(Role role) throws SQLException {
        /*
            chiude la connessione precedente
            memorizza il nuovo ruolo
            apre una connessione con l’account tecnico corrispondente
        */
        close();
        currentRole = role.name(); // MANAGER, CAMERIERE, PIZZAIOLO, BARMAN
        connection = openConnection(currentRole);
    }

    // Un metodo synchronized permette a un solo thread alla volta di modificare la connessione condivisa.
    public static synchronized void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                // L’eccezione durante la chiusura viene ignorata perché la connessione viene comunque abbandonata.
            } finally {
                connection = null;
            }
        }
    }

    private static Connection openConnection(String role) throws SQLException {
        Properties properties = loadProperties(); // carica il file db.properties
        /*
            CONNECTION_URL indica:
                protocollo: JDBC;
                DBMS: MySQL;
                server: localhost;
                porta: 3306;
                schema: Pizzeria.
        */
        String url = getRequiredProperty(properties, "CONNECTION_URL");

        // recupera nome account tecnico
        String user = getRequiredProperty(properties, role + "_USER");
        // recupera password account tecnico
        String password = getRequiredProperty(properties, role + "_PASS");
        // questa istruzione funziona grazie al driver JDBC
        return DriverManager.getConnection(url, user, password); // punto in cui viene stabilita la vera connessione con MySQL.
    }

    private static Properties loadProperties() throws SQLException {
        // Carica il contenuto di db.properties in un oggetto Properties.
        Properties properties = new Properties();

        try (InputStream input = openPropertiesFile()) {
            properties.load(input); // carica il file db.properties
            return properties; // ritorna l'oggetto
        } catch (IOException e) {
            throw new SQLException("Impossibile leggere resources/db.properties", e);
        }
    }

    private static InputStream openPropertiesFile() throws IOException {
        // legge il file db.properties dal classpath
        InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");

        if (input != null) {
            return input;
        }

        // se non lo trova usa la cartella del progetto
        return new FileInputStream("resources/db.properties");
    }

    private static String getRequiredProperty(Properties properties, String name) throws SQLException {
        /*
        Controlla che una proprietà:
        esista;
        non sia vuota.
        Se manca, genera una SQLException.
        */
        String value = properties.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            throw new SQLException("Proprietà mancante: " + name);
        }
        return value;
    }
}
